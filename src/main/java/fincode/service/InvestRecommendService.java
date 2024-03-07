package fincode.service;

import fincode.model.InvestRecommendRes;

import java.util.List;

/**
 * @author zlj
 * @date 2023/3/23
 */
public interface InvestRecommendService {

    List<InvestRecommendRes> list(int page, int limit);
}
