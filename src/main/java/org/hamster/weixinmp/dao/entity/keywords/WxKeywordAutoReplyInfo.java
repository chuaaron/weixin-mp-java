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

import org.hamster.weixinmp.dao.entity.auth.WxAuthorizationFuncInfo;
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
@Table(name = "wx_keyword_autoreply_info")
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class WxKeywordAutoReplyInfo extends WxBaseEntity{
	@SerializedName("list")
	private List<WxKeywordRule> list;
	
	
	@Override	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		if (list != null){
			sb.append(list);
		}
		return sb.toString();
	}
}

