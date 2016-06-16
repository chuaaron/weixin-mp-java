/**
 * 
 */
package org.hamster.weixinmp.dao.entity.keywords;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hamster.weixinmp.dao.entity.auth.WxAuthorizationFuncScopeCategory;
import org.hamster.weixinmp.dao.entity.base.WxBaseEntity;
import org.hamster.weixinmp.dao.entity.msg.WxNewsInfoContainer;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * 
 * @author grossopaforever@gmail.com
 * @version Aug 3, 2013
 * 
 */
@Entity
@Table(name = "wx_autoreply_info")
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxAutoReplyInfo extends WxBaseEntity{
	@SerializedName("type")
	@Column(name = "type", length = 200, nullable = false)
	private String type;
	@SerializedName("content")
	@Column(name = "content", length = 200, nullable = false)
	private String content;
	@SerializedName("news_info")
	private WxNewsInfoContainer newsInfo;
	
	
	@Override	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if (type != null)
			sb.append("type:" + type);
		if (content != null)
			sb.append("content:" + content);
		if (newsInfo != null)
			sb.append(newsInfo);
		return sb.toString();
	}
}

