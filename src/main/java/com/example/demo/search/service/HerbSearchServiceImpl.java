package com.example.demo.search.service;

import java.io.File;
import java.io.StringReader;
import java.nio.file.FileSystems;
import java.util.*;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;

import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.parameter.domain.ParameterBean;
import com.example.demo.parameter.service.HerbParameterService;
import com.example.demo.tool.FileUtil;

@Service
public class HerbSearchServiceImpl implements HerbSearchService{
	
	@Autowired
	private HerbParameterService pservice;
	
//	public static final String INDEX_PATH = "F:/lucene/"; // 存放Lucene索引文件的位置
//	public static final String SCAN_PATH = "F:/text/"; // 需要被扫描的位置，测试的时候记得多在这下面放一些文件

	@Override
	public String createIndex() {
		Map<String,String> pmap = new HashMap<String,String>();
		List<ParameterBean> list =  pservice.selectAll();
		for(ParameterBean p : list)
			pmap.put(p.getName(), p.getValue());
		String INDEX_PATH = pmap.get("luencescan");
		String SCAN_PATH = pmap.get("luencetext");
		IndexWriter indexWriter = null;
		try{
			Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_PATH));
		//	StandardAnalyzer analyzer = new StandardAnalyzer();
			CharArraySet cas = new CharArraySet(0, true);
			SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(cas);
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriter = new IndexWriter(directory, indexWriterConfig);
			indexWriter.deleteAll();// 清除以前的index
			// 获取被扫描目录下的所有文件，包括子目录
			List<File> files = FileUtil.listAllFiles(SCAN_PATH);
			for(int i=0; i<files.size(); i++){
				Document document = new Document();
				File file = files.get(i);
				document.add(new Field("content", FileUtil.readFile(file.getAbsolutePath()), TextField.TYPE_STORED));
				document.add(new Field("fileName", file.getName(), TextField.TYPE_STORED));
				document.add(new Field("filePath", file.getAbsolutePath(), TextField.TYPE_STORED));
				document.add(new Field("updateTime", file.lastModified()+"", TextField.TYPE_STORED));
				indexWriter.addDocument(document);
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(indexWriter != null) indexWriter.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Map<String,Object> searchAll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String,Object> searchByQuery(Map<String, Object> map) {
		Map<String,String> pmap = new HashMap<String,String>();
		List<ParameterBean> list =  pservice.selectAll();
		for(ParameterBean p : list)
			pmap.put(p.getName(), p.getValue());
		String INDEX_PATH = pmap.get("luencescan");
	//	String SCAN_PATH = pmap.get("luencetext");
        String keyWord = (String) map.get("content");
    //  String size = (String) map.get("pagesize");
        String number = (String) map.get("pagenumber");
        int numberpage = Integer.parseInt(number);
        Map<String,Object> mmap = new HashMap<String,Object>();
		DirectoryReader directoryReader = null;
		IndexSearcher indexSearcher = null;
		try{
			// 1、创建Directory
			Directory directory = FSDirectory.open(FileSystems.getDefault().getPath(INDEX_PATH));
			// 2、创建IndexReader
			directoryReader = DirectoryReader.open(directory);
			// 3、根据IndexReader创建IndexSearch
			indexSearcher = new IndexSearcher(directoryReader);
			// 4、创建搜索的Query
		//	StandardAnalyzer analyzer = new StandardAnalyzer();
			CharArraySet cas = new CharArraySet(0, true);
			SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(cas);// 使用分词
			
			// 简单的查询，创建Query表示搜索域为content包含keyWord的文档
			//Query query = new QueryParser("content", analyzer).parse(keyWord);
			
			String[] fields = {"fileName", "content"}; // 要搜索的字段，一般搜索时都不会只搜索一个字段
			// 字段之间的与或非关系，MUST表示and，MUST_NOT表示not，SHOULD表示or，有几个fields就必须有几个clauses
			BooleanClause.Occur[] clauses = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
			// MultiFieldQueryParser表示多个域解析， 同时可以解析含空格的字符串，如果我们搜索"上海 中国" 
			Query multiFieldQuery = MultiFieldQueryParser.parse(keyWord, fields, clauses, analyzer);
			
			// 5、根据searcher搜索并且返回TopDocs
			TopDocs topDocs = indexSearcher.search(multiFieldQuery, 9999); // 搜索前9999条结果
		//	System.out.println("共找到匹配处：" + topDocs.totalHits); // totalHits和scoreDocs.length的区别还没搞明白
			mmap.put("matchcount", topDocs.totalHits);
			// 6、根据TopDocs获取ScoreDoc对象
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		//	System.out.println("共找到匹配文档数：" + scoreDocs.length);
			int totaldoc = scoreDocs.length ;
			mmap.put("matchfilecount", totaldoc);
			QueryScorer scorer = new QueryScorer(multiFieldQuery, "content");
			// 自定义高亮代码
			SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<span id=\"hight\">", "</span>");
			Highlighter highlighter = new Highlighter(htmlFormatter, scorer);
		//	highlighter.setTextFragmenter(new SimpleSpanFragmenter(scorer));
		//	for (ScoreDoc scoreDoc : scoreDocs)
		//	for (int i=0;i<scoreDocs.length;i++)
		//	{
				
			//	QueryBean bean = new QueryBean();
				// 7、根据searcher和ScoreDoc对象获取具体的Document对象
			if(totaldoc != 0){
				Document document = indexSearcher.doc(scoreDocs[numberpage-1].doc);
				//TokenStream tokenStream = new SimpleAnalyzer().tokenStream("content", new StringReader(content));
				//TokenSources.getTokenStream("content", tvFields, content, analyzer, 100);
				//TokenStream tokenStream = TokenSources.getAnyTokenStream(indexSearcher.getIndexReader(), scoreDoc.doc, "content", document, analyzer);
				//System.out.println(highlighter.getBestFragment(tokenStream, content));
			//	System.out.println("-----------------------------------------");
			//	System.out.println(document.get("fileName") + ":" + document.get("filePath"));
			//	bean.setFilename(document.get("fileName"));
				String longcontent= document.get("content"); 
				String filename = document.get("fileName");
				mmap.put("filename", filename);
				int index = filename.lastIndexOf(".");
				mmap.put("name", filename.substring(0, index));
			//	bean.setFilepath(document.get("filePath"));
				mmap.put("filepath", document.get("filePath"));
				highlighter.setTextFragmenter(new SimpleFragmenter(longcontent.length())); 
				TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(document.get("content")));
				String hightContent = highlighter.getBestFragment(tokenStream, longcontent);
				hightContent = hightContent == null? "":hightContent;
			//	String hightContent = highlighter.getBestFragment(analyzer, "content", document.get("content"));
			//	bean.setContent(hightContent);//<span style="backgroud:brown">hello</span> world. �����ˡ�
				mmap.put("content", hightContent);
			//	System.out.println(highlighter.getBestFragment(analyzer, "content", document.get("content")));
			//	System.out.println("");
			}
		//	}
		//	mmap.put("query", bean);
		}catch (Exception e){
			e.printStackTrace();
		}finally{    
			try{
				if(directoryReader != null) directoryReader.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return mmap;
	}

	@Override
	public String updateIndex() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteIndex() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static void main(String[] args) {
		new HerbSearchServiceImpl().createIndex(); 
	}


	
}
