package com.mmall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    public ServerResponse add(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);

        int rowCount = shippingMapper.insert(shipping);
        if(rowCount > 0) {
            Map result = Maps.newHashMap();
            result.put("shippingId", shipping.getId());
            return ServerResponse.createBySuccess("Add shipping successfully", result);
        }

        return ServerResponse.createByErrorMessage("Add shipping unsuccessfully");
    }

    public ServerResponse<String> del(Integer userId, Integer shippingId) {

        int rowCount = shippingMapper.deleteByShippingIdUserId(userId, shippingId);
        if(rowCount > 0) {
            return ServerResponse.createBySuccess("Delete shipping successfully");
        }

        return ServerResponse.createByErrorMessage("Delete shipping unsuccessfully");
    }

    public ServerResponse update(Integer userId, Shipping shipping) {
        shipping.setUserId(userId);

        // Check userId
        int rowCount = shippingMapper.updateByShipping(shipping);
        if(rowCount > 0) {
            return ServerResponse.createBySuccess("Update shipping successfully");
        }

        return ServerResponse.createByErrorMessage("Update shipping unsuccessfully");
    }

    public ServerResponse<Shipping> select(Integer userId, Integer shippingId) {

        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId, shippingId);
        if(shipping == null) {
            return ServerResponse.createByErrorMessage("Select shipping unsuccessfully");
        }

        return ServerResponse.createBySuccess("Select shipping successfully", shipping);
    }

    public ServerResponse<PageInfo> list(Integer userId, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippinglist = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippinglist);

        return ServerResponse.createBySuccess(pageInfo);
    }
}
