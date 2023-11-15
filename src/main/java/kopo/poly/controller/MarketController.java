package kopo.poly.controller;

import kopo.poly.dto.MarketDTO;
import kopo.poly.service.IMarketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping(value = "/market")
@RequiredArgsConstructor
public class MarketController {

    private final IMarketService marketService;

    // 시장 목록 조회 코드
    // 구현완료(11/13)
    @GetMapping(value = "/list")
    @ResponseBody
    public List<String> getMarketList(HttpServletRequest request)
            throws Exception {
        log.info(this.getClass().getName() + ".list Start!");

        String nm = request.getParameter("shopCode");
        List<MarketDTO> rList = marketService.getMarketList(nm);
        if (rList == null) rList = new ArrayList<>();

        List<String> list = new ArrayList<>();

        for (MarketDTO dto : rList) {
            String text = dto.getMarketName() + "[" + dto.getMarketLocation() + "]";
            list.add(text);
        }

        log.info(this.getClass().getName() + ".list End!");
        return list;
    }

    // 마커 코드
    @GetMapping(value = "/marker")
    @ResponseBody
    public List<MarketDTO> marker() throws Exception {
        log.info(this.getClass().getName() + ".marker Start!");

        List<MarketDTO> pList = marketService.getMarketList("");
        if (pList == null) pList = new ArrayList<>();

        log.info(pList.toString());

        log.info(this.getClass().getName() + ".marker End!");
        return pList;
    }
}
