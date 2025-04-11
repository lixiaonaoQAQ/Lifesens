package com.Lifesens.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Lifesens.entity.OrderInfo;
import com.Lifesens.entity.OrderItem;
import com.Lifesens.entity.UserLogin;
import com.Lifesens.repository.OrderInfoRepository;
import com.Lifesens.repository.OrderItemRepository;
import com.Lifesens.repository.UserLoginRepository;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    // 注文一覧表示
    @GetMapping
    public String listOrders(@RequestParam(value = "status", required = false) String status,
                             Model model) {
        List<OrderInfo> orders;

        if (status != null && !status.isBlank() && !"ALL".equals(status)) {
            orders = orderInfoRepository.findByStatusOrderByCreatedAtDesc(status);
        } else {
            orders = orderInfoRepository.findAllByOrderByCreatedAtDesc();
        }

        // ユーザー名を取得して仮で受取人にセット
        for (OrderInfo order : orders) {
            Optional<UserLogin> userOpt = userLoginRepository.findById(order.getUserId());
            userOpt.ifPresent(user -> order.setReceiverName(user.getUsername()));
        }

        model.addAttribute("orders", orders);
        model.addAttribute("selectedStatus", status); // 選択中のステータスを渡す
        return "admin_order_list";
    }


    // 注文詳細表示
    @GetMapping("/{id}")
    public String orderDetail(@PathVariable("id") Integer id, Model model) {
        Optional<OrderInfo> orderOpt = orderInfoRepository.findById(id);
        if (orderOpt.isEmpty()) {
            return "redirect:/admin/orders";
        }

        OrderInfo order = orderOpt.get();
        List<OrderItem> items = orderItemRepository.findByOrderId(order.getOrderId());

        model.addAttribute("order", order);
        model.addAttribute("items", items);

        return "admin_order_detail";
    }

    // ステータス更新処理
    @PostMapping("/update-status")
    public String updateOrderStatus(@RequestParam Integer orderId,
                                    @RequestParam String status,
                                    RedirectAttributes redirectAttributes) {

        Optional<OrderInfo> orderOpt = orderInfoRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            OrderInfo order = orderOpt.get();
            order.setStatus(status);
            order.setUpdatedAt(LocalDateTime.now());
            orderInfoRepository.save(order);

            redirectAttributes.addFlashAttribute("success", "ステータスを更新しました。");
        } else {
            redirectAttributes.addFlashAttribute("error", "該当する注文が見つかりませんでした。");
        }

        return "redirect:/admin/orders/" + orderId;
    }
}
