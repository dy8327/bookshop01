package com.example.bookshop01.goods.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.bookshop01.common.base.BaseController;
import com.example.bookshop01.goods.service.GoodsService;
import com.example.bookshop01.goods.vo.GoodsVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@Controller("goodsController")
@RequestMapping(value="/goods")
public class GoodsControllerImpl extends BaseController   implements GoodsController {
	@Autowired
	private GoodsService goodsService;
	
	@RequestMapping(value="/goodsDetail.do" ,method = RequestMethod.GET)
	public ModelAndView goodsDetail(@RequestParam("goods_id") String goods_id,
			                       HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=(String)request.getAttribute("viewName");
		HttpSession session=request.getSession();
		Map<String, Object> goodsMap=goodsService.goodsDetail(goods_id);
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("goodsMap", goodsMap);
		GoodsVO goodsVO=(GoodsVO)goodsMap.get("goodsVO");
		addGoodsInQuick(goods_id,goodsVO,session);
		return mav;
	}

	@Override
@RequestMapping(value="/keywordSearch.do", method = RequestMethod.GET)
@ResponseBody
public Map<String, List<String>> keywordSearch(@RequestParam("keyword") String keyword,
                                               HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {

    Map<String, List<String>> resultMap = new HashMap<>();

    if (keyword == null || keyword.trim().isEmpty()) {
        resultMap.put("keyword", new ArrayList<>());
        return resultMap;
    }

    keyword = keyword.toUpperCase();

    List<String> keywordList = goodsService.keywordSearch(keyword);
    resultMap.put("keyword", keywordList);

    return resultMap;
}
	
	@RequestMapping(value="/searchGoods.do" ,method = RequestMethod.GET)
	public ModelAndView searchGoods(@RequestParam("searchWord") String searchWord,
			                       HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName=(String)request.getAttribute("viewName");
		List<GoodsVO> goodsList=goodsService.searchGoods(searchWord);
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("goodsList", goodsList);
		return mav;
		
	}
	
	@SuppressWarnings("unchecked")
	private void addGoodsInQuick(String goods_id,GoodsVO goodsVO,HttpSession session){
		boolean already_existed=false;
		List<GoodsVO> quickGoodsList =
                (List<GoodsVO>) session.getAttribute("quickGoodsList");
		
		if(quickGoodsList==null){
			quickGoodsList = new ArrayList<>();
		}
			if(quickGoodsList.size() < 4){ //�̸��� ��ǰ ����Ʈ�� ��ǰ������ ���� ������ ���
				for(GoodsVO quickGoods : quickGoodsList){
					if(goods_id.equals(String.valueOf(quickGoods.getGoods_id()))){
						already_existed=true;
						break;
					}
				}
				if(already_existed==false){
					quickGoodsList.add(goodsVO);
				}
			}
			
		session.setAttribute("quickGoodsList",quickGoodsList);
		session.setAttribute("quickGoodsListNum", quickGoodsList.size());
	}
}
