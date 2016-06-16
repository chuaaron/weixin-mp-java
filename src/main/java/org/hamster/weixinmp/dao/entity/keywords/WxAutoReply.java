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

import org.hamster.weixinmp.dao.entity.base.WxBaseEntity;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * 
 * @author grossopaforever@gmail.com
 * @version Aug 3, 2013
 * 
 */
@Entity
@Table(name = "wx_autoreply")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxAutoReply extends WxBaseEntity{
	@SerializedName("is_add_friend_reply_open")
	@Column(name = "is_add_friend_reply_open", nullable = false)
	private Integer isAddFriendReplyOpen;
	@SerializedName("is_autoreply_open")
	@Column(name = "is_autoreply_open", nullable = false)
	private Integer isAutoReplyOpen;
	@SerializedName("add_friend_autoreply_info")
	@Column(name = "add_friend_autoreply_info", nullable = false)	
	private WxAutoReplyInfo addFriendAutoReplyInfo;
	@SerializedName("message_default_autoreply_info")
	@Column(name = "message_default_autoreply_info", nullable = false)		
	private WxAutoReplyInfo msgDefaultAutoReplyInfo;		
	@SerializedName("keyword_autoreply_info")
	@Column(name = "keyword_autoreply_info", nullable = false)			
	private WxKeywordAutoReplyInfo keywordAutoReplyInfo;			

	public String autoReplyJsonStr;
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if (isAddFriendReplyOpen != null)
			sb.append("is_add_friend_reply_open:" + isAddFriendReplyOpen);
		if (addFriendAutoReplyInfo != null)
			sb.append(addFriendAutoReplyInfo);			
		if (isAutoReplyOpen != null)
			sb.append("is_autoreply_open:" + isAutoReplyOpen);
		if (msgDefaultAutoReplyInfo != null)
			sb.append(msgDefaultAutoReplyInfo);
		if (keywordAutoReplyInfo != null)
			sb.append(keywordAutoReplyInfo);		
		return sb.toString();
	}
}

