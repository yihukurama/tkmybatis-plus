package com.yihukurama.tkmybatisplus.app.utils;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 功能描述:XML格式的文档处理工具类;
 * @Author:liujun
 * @Date:2016年10月11日 下午12:50:21
 */
public class XmlUtils
{
	private static final SAXParserFactory SAX_PARSER_FACTORY = SAXParserFactory.newInstance();

	/**
	 * 功能描述:将XML格式的文本文件解析为Map格式的键值对数据,Map集合中key对应xml文本中的元素节点名称,value对应xml文本中元素节点的值;
	 * @param content xml文本内容
	 * @param charset xml文本编码格式
	 * @return 解析成功返回Map集合,解析失败返回null
	 * @Author:liujun
	 * @Date:2016年10月11日 下午12:59:35
	 */
	public static Map<String, String> saxParse(String content, String charset)
	{
		try
		{
			SAXParser parser = SAX_PARSER_FACTORY.newSAXParser();
			// 将字符串格式的XML文档转换为字节流;
			InputStream input = new ByteArrayInputStream(content.getBytes(charset));
			SaxHandler handler = new SaxHandler();
			parser.parse(input, handler);
			
			return handler.getDataMap();
		}
		catch(ParserConfigurationException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(SAXException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
}
