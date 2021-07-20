package com.sjzc.kt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjzc.kt.dao.TbstaticMapper;
import com.sjzc.kt.entity.Tbstatic;
import com.sjzc.kt.entity.TbstaticExample;
import com.sjzc.kt.util.R;

@RestController
@RequestMapping("static")
public class StaticController {

	@Autowired
	private TbstaticMapper tbstaticMapper;
	
	@GetMapping("/selectDepartment")
	public R selectDepartment() {
		
		TbstaticExample tbstaticExample1 = new TbstaticExample();
		tbstaticExample1.setOrderByClause("sort asc");
		tbstaticExample1.createCriteria().andTypeEqualTo(1).andDelStateEqualTo(1).andParentEqualTo(0);
		
		List<Tbstatic> departmentList = tbstaticMapper.selectByExample(tbstaticExample1);
		if (departmentList.size()>0 && !departmentList.isEmpty()) {
			for (Tbstatic d:departmentList) {
				TbstaticExample tbstaticExample2 = new TbstaticExample();
				tbstaticExample2.setOrderByClause("sort asc");
				tbstaticExample2.createCriteria().andParentEqualTo(d.getId()).andDelStateEqualTo(1);
				List<Tbstatic> majorList = tbstaticMapper.selectByExample(tbstaticExample2);
				d.setTbstaticList(majorList);
				if (majorList.size()>0 && !majorList.isEmpty()) {
					for (Tbstatic m:majorList) {
						TbstaticExample tbstaticExample3 = new TbstaticExample();
						tbstaticExample3.setOrderByClause("sort asc");
						tbstaticExample3.createCriteria().andParentEqualTo(m.getId()).andDelStateEqualTo(1);
						List<Tbstatic> gradeList = tbstaticMapper.selectByExample(tbstaticExample3);
						m.setTbstaticList(gradeList);
					}
				}
			}
		}
		return R.ok().put("departmentList", departmentList);
	}
}
