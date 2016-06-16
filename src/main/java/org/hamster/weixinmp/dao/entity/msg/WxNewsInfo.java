/**
 * 
 */
package org.hamster.weixinmp.dao.entity.msg;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hamster.weixinmp.config.WxConfig;
import org.hamster.weixinmp.constant.WxMsgType;
import org.hamster.weixinmp.dao.entity.auth.WxAuthorizationFuncInfo;
import org.hamster.weixinmp.dao.entity.auth.WxAuthorizationInfo;
import org.hamster.weixinmp.dao.entity.base.WxBaseEntity;
import org.hamster.weixinmp.dao.entity.base.WxBaseMsgEntity;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * @author grossopaforever@gmail.com
 * @version Jul 28, 2013
 * 
 */
@Entity
@Table(name = "wx_newsinfo")
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxNewsInfo extends WxBaseEntity{
	@SerializedName("title")
	@Column(name = "title", length = 200, nullable = false)
	private String title;
	@SerializedName("author")
	@Column(name = "author", length = 200, nullable = false)
	private String author;
	@SerializedName("digest")
	@Column(name = "digest", length = 200, nullable = false)
	private String digest;
	@SerializedName("show_cover")
	@Column(name = "show_cover", nullable = true)
	private Integer showCover;	
	@SerializedName("cover_url")
	@Column(name = "cover_url", length = 200, nullable = false)
	private String coverUrl;
	@SerializedName("content_url")
	@Column(name = "content_url", length = 200, nullable = false)
	private String contentUrl;
	@SerializedName("source_url")
	@Column(name = "source_url", length = 200, nullable = false)
	private String sourceUrl;

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if (title != null)
			sb.append("title:" + title);
		if (author != null)
			sb.append("author:" + author);
		if (digest != null)
			sb.append("digest:" + digest);
		if (showCover != null)
			sb.append("show_cover:" + showCover);
		if (coverUrl != null)
			sb.append("cover_url:" + coverUrl);
		if (contentUrl != null)
			sb.append("content_url:" + contentUrl);
		if (sourceUrl != null)
			sb.append("source_url:" + sourceUrl);
		return sb.toString();
	}	
	
}
