/**
 * 
 */
package org.hamster.weixinmp.service;

import static org.hamster.weixinmp.util.WxUtil.getAccessTokenParams;
import static org.hamster.weixinmp.util.WxUtil.sendRequest;
import static org.hamster.weixinmp.util.WxUtil.toJsonString;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hamster.weixinmp.config.WxConfig;
import org.hamster.weixinmp.constant.WxMsgRespType;
import org.hamster.weixinmp.constant.WxMsgRespTypeEnum;
import org.hamster.weixinmp.constant.WxMsgTypeEnum;
import org.hamster.weixinmp.controller.util.WxXmlUtil;
import org.hamster.weixinmp.dao.entity.base.WxBaseMsgEntity;
import org.hamster.weixinmp.dao.entity.base.WxBaseRespEntity;
import org.hamster.weixinmp.dao.entity.resp.WxRespImageEntity;
import org.hamster.weixinmp.dao.entity.resp.WxRespMusicEntity;
import org.hamster.weixinmp.dao.entity.resp.WxRespPicDescEntity;
import org.hamster.weixinmp.dao.entity.resp.WxRespShortVideoEntity;
import org.hamster.weixinmp.dao.entity.resp.WxRespTextEntity;
import org.hamster.weixinmp.dao.entity.resp.WxRespVideoEntity;
import org.hamster.weixinmp.dao.entity.resp.WxRespVoiceEntity;
import org.hamster.weixinmp.dao.entity.resp.WxRespInitEntity;
import org.hamster.weixinmp.exception.WxException;
import org.hamster.weixinmp.model.WxRespCode;
import org.hamster.weixinmp.service.handler.WxMessageHandlerIfc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author grossopaforever@gmail.com
 * @version Dec 30, 2013
 * 
 */
@Service
public class WxMessageService {
	@Autowired
	WxConfig config;

	@Autowired(required = false)
	List<WxMessageHandlerIfc> handlers;

	@Autowired(required = false)
	private WxStorageService wxStorageService;

	public WxBaseMsgEntity parseXML(String xml) throws DocumentException,
			WxException {
		Element ele = DocumentHelper.parseText(xml).getRootElement();
		String msgType = null;
		if ((msgType = ele.elementText("MsgType")) == null) {
			throw new WxException("cannot find MsgType Node!\n" + xml);
		}
		WxMsgTypeEnum msgTypeEnum = WxMsgTypeEnum.inst(msgType);
		switch (msgTypeEnum) {
		case EVENT:
			// wxStorageService.saveMsgEvent(ele);
			return WxXmlUtil.getMsgEvent(ele);
		case IMAGE:
			// wxStorageService.saveMsgImg(ele);
			return WxXmlUtil.getMsgImage(ele);
		case LINK:
			// wxStorageService.saveMsgLink(ele);
			return WxXmlUtil.getMsgLink(ele);
		case LOCATION:
			// wxStorageService.saveMsgLoc(ele);
			return WxXmlUtil.getMsgLoc(ele);
		case TEXT:
			// wxStorageService.saveMsgText(ele);
			return WxXmlUtil.getMsgText(ele);
		case VIDEO:
			return WxXmlUtil.getMsgVideo(ele);
		case SHORTVIDEO: 
			return WxXmlUtil.getMsgShortVideo(ele);
		case VOICE:
			return WxXmlUtil.getMsgVoice(ele);
		case INIT:
			return WxXmlUtil.getMsgInit(ele);
		default:
			// never happens
			break;
		}
		return null;
	}

	public WxBaseRespEntity handleMessage(WxBaseMsgEntity msg) {
		List<WxMessageHandlerIfc> handlerList = new ArrayList<WxMessageHandlerIfc>();
		if (handlers != null) {
			handlerList.addAll(handlers);
		}
		Collections.sort(handlerList, new WxMessageHandlerComparator());

		Map<String, Object> context = new HashMap<String, Object>();
		WxBaseRespEntity result = null;
		for (WxMessageHandlerIfc handler : handlerList) {
			result = handler.handle(msg, context);
		}

		if (result == null) {
			result = defaultResult(msg.getToUserName(), msg.getFromUserName());
		}
		return result;
	}

	public Element parseRespXML(WxBaseRespEntity resp) throws DocumentException {
		WxMsgRespTypeEnum type = WxMsgRespTypeEnum.inst(resp.getMsgType());
		switch (type) {
		case IMAGE:
			return WxXmlUtil.getRespImage((WxRespImageEntity) resp);
		case MUSIC:
			return WxXmlUtil.getRespMusic((WxRespMusicEntity) resp,
					((WxRespMusicEntity) resp).getThumb());
		case NEWS:
			return WxXmlUtil.getRespPicDesc((WxRespPicDescEntity) resp);
		case TEXT:
			return WxXmlUtil.getRespTextXML((WxRespTextEntity) resp);
		case VIDEO:
			return WxXmlUtil.getRespVideo((WxRespVideoEntity) resp);
		case SHORTVIDEO:
			return WxXmlUtil.getRespShortVideo((WxRespShortVideoEntity) resp);
		case VOICE:
			return WxXmlUtil.getRespVoice((WxRespVoiceEntity) resp);
		case INIT:
			return WxXmlUtil.getRespInitXML((WxRespInitEntity) resp);
		default:
			break;
		}
		return null;
	}

	protected WxRespTextEntity defaultResult(String fromUserName,
			String toUserName) {
		WxRespTextEntity result = new WxRespTextEntity();
		result.setContent("hello, received your message. 您好,您的消息已收到.");
		result.setCreatedDate(new Date());
		result.setCreateTime(new Date().getTime() / 1000);
		result.setFromUserName(fromUserName);
		result.setMsgType(WxMsgRespType.TEXT);
		result.setToUserName(toUserName);
		return result;
	}
	
	public String buildJsonTextMessage(String toUser, String fromUser, String content) {
		Map<String, Object> requestJson = new HashMap<String, Object>();
		requestJson.put("touser", toUser);
		requestJson.put("fromuser", fromUser);
		requestJson.put("msgtype", "text");
		Map<String, Object> textJson = new HashMap<String, Object>();
		textJson.put("content", content);
		requestJson.put("text", textJson);
		
		return toJsonString(requestJson);
	}
	
	public String buildJsonInitMessage(String toUser, String fromUser, String content, String lcGroupId) {
		Map<String, Object> requestJson = new HashMap<String, Object>();
		requestJson.put("touser", toUser);
		requestJson.put("fromuser", fromUser);
		requestJson.put("msgtype", "text");
		Map<String, Object> initJson = new HashMap<String, Object>();
		initJson.put("content", content);
		initJson.put("lc_groupid", lcGroupId);
		requestJson.put("text", initJson);
		
		return toJsonString(requestJson);
	}
	
	public String buildJsonImageMessage(String toUser, String fromUser, String mediaId) {
		Map<String, Object> requestJson = new HashMap<String, Object>();
		requestJson.put("touser", toUser);
		requestJson.put("fromuser", fromUser);
		requestJson.put("msgtype", "image");
		Map<String, Object> imageJson = new HashMap<String, Object>();
		imageJson.put("media_id", mediaId);
		requestJson.put("image", imageJson);
		
		return toJsonString(requestJson);
	}

	public String buildJsonVoiceMessage(String toUser, String fromUser, String mediaId) {
		Map<String, Object> requestJson = new HashMap<String, Object>();
		requestJson.put("touser", toUser);
		requestJson.put("fromuser", fromUser);
		requestJson.put("msgtype", "voice");
		Map<String, Object> voiceJson = new HashMap<String, Object>();
		voiceJson.put("media_id", mediaId);
		requestJson.put("voice", voiceJson);
		
		return toJsonString(requestJson);
	}
	public String buildJsonVideoMessage(String toUser, String fromUser, String mediaId, String description, String title, String thumbMediaId) {
		Map<String, Object> requestJson = new HashMap<String, Object>();
		requestJson.put("touser", toUser);
		requestJson.put("fromuser", fromUser);
		requestJson.put("msgtype", "video");
		Map<String, Object> videoJson = new HashMap<String, Object>();
		videoJson.put("media_id", mediaId);
		requestJson.put("video", videoJson);
		
		return toJsonString(requestJson);
	}
	public String buildJsonNewsMessage(String toUser, String fromUser, String mediaId) {
		Map<String, Object> requestJson = new HashMap<String, Object>();
		requestJson.put("touser", toUser);
		requestJson.put("fromuser", fromUser);
		requestJson.put("msgtype", "mpnews");
		Map<String, Object> newsJson = new HashMap<String, Object>();
		newsJson.put("media_id", mediaId);
		requestJson.put("mpnews", newsJson);
		
		return toJsonString(requestJson);
	}	

	public static String buildJsonArticleMessage(String toUser, String fromUser, String title, String description, String url, String picUrl) {
		Map<String, Object> requestJson = new HashMap<String, Object>();
		requestJson.put("touser", toUser);
		requestJson.put("fromuser", fromUser);
		requestJson.put("msgtype", "news");
		Map<String, Object> articleHolderJson = new HashMap<String, Object>();
		Map<String, Object> articleJson = new HashMap<String, Object>();
		ArrayList<Map<String, Object>> articleArrayHolder = new ArrayList<Map<String, Object>>();
		articleJson.put("title", title);
		articleJson.put("description", description);
		articleJson.put("url", url);
		articleJson.put("picurl", picUrl);
		articleArrayHolder.add(articleJson);
		articleHolderJson.put("articles", articleArrayHolder);
		requestJson.put("news", articleHolderJson);	
		return toJsonString(requestJson);
	}
	
	public WxRespCode sendMessage(String accessToken, String toUserName, String content) throws WxException {
		WxRespCode result = sendRequest(config.getCustomSendUrl(),
										HttpMethod.POST,
										getAccessTokenParams(accessToken),
										new StringEntity(content, Consts.UTF_8),
										WxRespCode.class);
		
		return result;
	}
	
	public byte[] downloadMedia(String accessToken, String mediaId) throws WxException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpRequestBase request = new HttpGet();
		
		try {
			URIBuilder builder = new URIBuilder("http://file.api.weixin.qq.com/cgi-bin/media/get");
			builder.addParameter("access_token", accessToken);
			builder.addParameter("media_id", mediaId);
			request.setURI(builder.build());
			
			HttpResponse response = client.execute(request);
			
			byte[] byteArrContent = IOUtils.toByteArray(response.getEntity().getContent());
			String respBody = IOUtils.toString(new ByteArrayInputStream(byteArrContent), "UTF-8");
			Gson gson = new Gson();
			if (respBody.indexOf("{\"errcode\"") == 0 || respBody.indexOf("{\"errmsg\"") == 0) {
				WxRespCode exJson = gson.fromJson(respBody,WxRespCode.class);
				throw new WxException(exJson);
			}
			
			return byteArrContent;
		} catch (IOException e) {
			throw new WxException(e);
		} catch (URISyntaxException e) {
			throw new WxException(e);
		}
	}
	
	public String uploadMedia(String accessToken, byte[] mediaContent, String mediaType) throws WxException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost();
		
		try {
			String mediaId = null;
			URIBuilder builder = new URIBuilder("http://file.api.weixin.qq.com/cgi-bin/media/upload");
			builder.addParameter("access_token", accessToken);
			builder.addParameter("type", mediaType);
			httpPost.setURI(builder.build());
			httpPost.addHeader("Content-type", "multipart/form-data");
			
			MultipartEntityBuilder mpBuilder = MultipartEntityBuilder.create();
			String mediaMimeType = "image/jpeg", fileExt = ".jpg";
			
			if (mediaType.equalsIgnoreCase("image")){
				mediaMimeType = "image/jpeg";
			    fileExt = ".jpg";
			} else if (mediaType.equalsIgnoreCase("voice")){
				mediaMimeType = "audio/mpeg";				
			    fileExt = ".mp3";				
			} else if (mediaType.equalsIgnoreCase("video")){
				mediaMimeType = "video/mp4";
				fileExt = ".mp4";
			}
			mpBuilder.addBinaryBody("media", mediaContent, ContentType.create(mediaMimeType),"agentupload" + fileExt);
			
			httpPost.setEntity(mpBuilder.build());
			
			HttpResponse response = client.execute(httpPost);
			
			String respBody = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
			Gson gson = new Gson();
			if (respBody.indexOf("{\"errcode\"") == 0 || respBody.indexOf("{\"errmsg\"") == 0) {
				WxRespCode exJson = gson.fromJson(respBody,WxRespCode.class);
				throw new WxException(exJson);
			} else {
				Map<String,Object> respMap;
				Type typeOfT = new TypeToken<Map<String,Object>>(){}.getType();
				respMap = gson.fromJson(respBody, typeOfT);
				mediaId = (String)respMap.get("media_id");
			}
			
			return mediaId;
		} catch (IOException e) {
			throw new WxException(e);
		} catch (URISyntaxException e) {
			throw new WxException(e);
		}
	}
	
	public static void main(String[] args){
	}
	
}

class WxMessageHandlerComparator implements Comparator<WxMessageHandlerIfc> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(WxMessageHandlerIfc o1, WxMessageHandlerIfc o2) {
		if (o1.priority() > o2.priority()) {
			return -1;
		} else if (o1.priority() < o2.priority()) {
			return 1;
		} else {
			return 0;
		}
	}
	
}
