package fincode.service;

import fincode.controller.Stock;
import fincode.model.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface StockService {
    List<StockPO> search(String keyWord);
    StockDetailVO getDetail(Integer stockId , String stockCode);
    StockFullInfoVO getFullInfo(Integer stockId);
    List<StockListVO> listStock(StockFrontListVO vo, HttpSession session, HttpServletRequest req, HttpServletResponse resp);



}
