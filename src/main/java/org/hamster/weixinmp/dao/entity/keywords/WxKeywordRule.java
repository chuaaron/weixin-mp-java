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
@Table(name = "wx_keywordrule")
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxKeywordRule extends WxBaseEntity{
	@SerializedName("rule_name")
	@Column(name = "rule_name", length = 200, nullable = false)
	private String ruleName;
	@SerializedName("reply_mode")
	@Column(name = "reply_mode", length = 200, nullable = false)
	private String replyMode;
	@SerializedName("keyword_list_info")
	private List<WxKeywordInfo> keywordListInfo;
	@SerializedName("reply_list_info")
	private List<WxAutoReplyInfo> replyListInfo;
	
	@Override	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if (ruleName != null)
			sb.append("rulename:" + ruleName);
		if (replyMode != null)
			sb.append("reply_mode:" + replyMode);
		if (keywordListInfo != null)
			sb.append(keywordListInfo);	
		if (replyListInfo != null)
			sb.append(replyListInfo);			
		return sb.toString();
	}
}

