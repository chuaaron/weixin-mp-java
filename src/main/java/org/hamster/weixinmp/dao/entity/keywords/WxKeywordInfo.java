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
@Table(name = "wx_keyword_info")
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxKeywordInfo extends WxBaseEntity{
	@SerializedName("type")
	@Column(name = "type", length = 200, nullable = false)
	private String type;
	@SerializedName("content")
	@Column(name = "content", length = 200, nullable = false)
	private String content;
	@SerializedName("match_mode")
	@Column(name = "match_mode", length = 200, nullable = true)
	private String matchMode;	
	
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if (type != null)
			sb.append("type:" + type);
		if (content != null)
			sb.append("content:" + content);
		if (matchMode != null)
			sb.append("matchmode:" + matchMode);
		return sb.toString();
	}	
}

