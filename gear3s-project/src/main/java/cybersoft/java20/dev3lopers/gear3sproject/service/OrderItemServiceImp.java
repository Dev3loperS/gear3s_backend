package cybersoft.java20.dev3lopers.gear3sproject.service;

import cybersoft.java20.dev3lopers.gear3sproject.dto.*;
import cybersoft.java20.dev3lopers.gear3sproject.entity.*;
import cybersoft.java20.dev3lopers.gear3sproject.payload.request.QueueRequest;
import cybersoft.java20.dev3lopers.gear3sproject.repository.OrderItemRepository;
import cybersoft.java20.dev3lopers.gear3sproject.repository.OrderRepository;
import cybersoft.java20.dev3lopers.gear3sproject.repository.ProductRepository;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.OrderItemService;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.ProductService;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemServiceImp implements OrderItemService {
    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Override
    public List<OrderItemDTO> findAll() {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setId(orderItem.getId());
            orderItemDTO.setAmount(orderItem.getAmount());
            orderItemDTO.setSubtotal(orderItem.getSubtotal());

            try {

                OrderDTO orderDTO = new OrderDTO();
                Orders order = orderItem.getOrder();
                orderDTO.setId(order.getId());
                orderDTO.setOrderDate(order.getOrderDate());
                orderDTO.setTotal(order.getTotal());
                orderDTO.setShippingAddress(order.getShippingAddress());


                UserCardDTO userCardDTO = new UserCardDTO();
                UserCard userCard = order.getUser_card();
                if (userCard != null) {
//                userCardDTO.setId(userCard.getId());

                    //set user in userCard
                    Users user = userCard.getUsers();
                    User_OrderDTO userOrderDTO = new User_OrderDTO();
                    userOrderDTO.setId(user.getId());
                    userOrderDTO.setEmail(user.getEmail());
                    userOrderDTO.setFullname(user.getFullname());
                    userOrderDTO.setBirthday(user.getBirthday());
                    userOrderDTO.setPhone(user.getPhone());
                    userOrderDTO.setAddress(user.getAddress());
                    userOrderDTO.setAvatar(user.getAvatar());

                    SexDTO sexDTO = new SexDTO();
                    Sex sex = order.getUser_card().getUsers().getSex();
                    try {
                        sexDTO.setId(sex.getId());
                        sexDTO.setName(sex.getName());
                        userOrderDTO.setSex(sexDTO);
                    } catch (Exception e) {
                        System.out.println("Error in OrderServiceImp - (findAll): " + e.getMessage());
                        return new ArrayList<>();
                    }

                    RoleDTO roleDTO = new RoleDTO();
                    Roles role = order.getUser_card().getUsers().getRoles();
                    try {
                        roleDTO.setId(role.getId());
                        roleDTO.setName(role.getName());
                        roleDTO.setDesc(role.getDescription());
                        userOrderDTO.setRoles(roleDTO);
                    } catch (Exception e) {
                        System.out.println("Error in OrderServiceImp - (findAll): " + e.getMessage());
                        return new ArrayList<>();
                    }

                    userCardDTO.setUsers(userOrderDTO);


                } else {

//                userCardDTO.setId(-1);
                    userCardDTO.setUsers(null);
//                userCardDTO.setMyCard(null);

                }

                OrderStatusDTO statusDTO = new OrderStatusDTO();
                OrderStatus status = order.getOrder_status();
                if (status != null) {
                    statusDTO.setId(status.getId());
                    statusDTO.setName(status.getName());


                } else {

                    statusDTO.setId(-1);
                    statusDTO.setName("");
                }

                Shipping shipping = order.getShipping();
                ShippingDTO shippingDTO = new ShippingDTO();
                if (shipping != null) {
                    shippingDTO.setId(shipping.getId());
                    shippingDTO.setPrice(shipping.getPrice());
                    shippingDTO.setName(shipping.getName());
//                userCard.setMyCard(order.getUsers().getU);

                } else {
                    shippingDTO.setId(-1);
                    shippingDTO.setPrice(0);
                    shippingDTO.setName("");
                }


                orderDTO.setShipping(shippingDTO);
                orderDTO.setStatus(statusDTO);
                orderDTO.setUser_card(userCardDTO);


                ///set orderDTO
                orderItemDTO.setOrder(orderDTO);

                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(orderItem.getProduct().getId());
                productDTO.setName(orderItem.getProduct().getName());
                productDTO.setPrice_origin(orderItem.getProduct().getOriginPrice());
                productDTO.setPrice_discount(orderItem.getProduct().getDiscountPrice());
                productDTO.setInventory(orderItem.getProduct().getInventory());
                productDTO.setSold_qty(orderItem.getProduct().getSoldQty());
                productDTO.setDescription(orderItem.getProduct().getDescription());
//                productDTO.setCreate_date(orderItem.getProduct().getCreate_date());
                productDTO.setView_qty(orderItem.getProduct().getView_qty());

                CategoryDTO categoryDTO = new CategoryDTO();
                Category category = orderItem.getProduct().getCategory();
                if (category == null) {
                    categoryDTO.setId(-1);
                    categoryDTO.setName("null");
                } else {
                    categoryDTO.setId(category.getId());
                    categoryDTO.setName(category.getName());
                }
//                productDTO.setCategoryDTO(categoryDTO);

                ///set productDTO
                orderItemDTO.setProduct(productDTO);

                orderItemDTOS.add(orderItemDTO);


            } catch (Exception e) {
                System.out.println("Error in OrderItemServiceImp- (findAll) : " + e.getMessage());
                orderItemDTOS = new ArrayList<>();

            }

        }
        return orderItemDTOS;
    }

    @Override
    public boolean deleteById(int id) {
        boolean isSuccess = false;
        try {
            orderItemRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println("Error in OrderItemServiceImp- (deleteById) : " + e.getMessage());
            return false;
        }
    }

    @Override
    public OrderItemDTO findByOrderId(int id) {
        OrderItem orderItem = orderItemRepository.findById(id);
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        if (orderItem != null) {

            orderItemDTO.setId(orderItem.getId());
            orderItemDTO.setAmount(orderItem.getAmount());
            orderItemDTO.setSubtotal(orderItem.getSubtotal());


            OrderDTO orderDTO = new OrderDTO();
            Orders order = orderItem.getOrder();
            orderDTO.setId(order.getId());
            orderDTO.setOrderDate(order.getOrderDate());
            orderDTO.setTotal(order.getTotal());
            orderDTO.setShippingAddress(order.getShippingAddress());


            UserCardDTO userCardDTO = new UserCardDTO();
            UserCard userCard = order.getUser_card();
            if (userCard != null) {
//                userCardDTO.setId(userCard.getId());

                //set user in userCard
                Users user = userCard.getUsers();
                User_OrderDTO userOrderDTO = new User_OrderDTO();
                userOrderDTO.setId(user.getId());
                userOrderDTO.setEmail(user.getEmail());
                userOrderDTO.setFullname(user.getFullname());
                userOrderDTO.setBirthday(user.getBirthday());
                userOrderDTO.setPhone(user.getPhone());
                userOrderDTO.setAddress(user.getAddress());
                userOrderDTO.setAvatar(user.getAvatar());

                SexDTO sexDTO = new SexDTO();
                Sex sex = order.getUser_card().getUsers().getSex();
                try {
                    sexDTO.setId(sex.getId());
                    sexDTO.setName(sex.getName());
                    userOrderDTO.setSex(sexDTO);
                } catch (Exception e) {
                    System.out.println("Error in OrderServiceImp - (findAll): " + e.getMessage());
                    return null;
                }

                RoleDTO roleDTO = new RoleDTO();
                Roles role = order.getUser_card().getUsers().getRoles();
                try {
                    roleDTO.setId(role.getId());
                    roleDTO.setName(role.getName());
                    roleDTO.setDesc(role.getDescription());
                    userOrderDTO.setRoles(roleDTO);
                } catch (Exception e) {
                    System.out.println("Error in OrderServiceImp - (findAll): " + e.getMessage());
                    return null;
                }

                userCardDTO.setUsers(userOrderDTO);


            } else {

//                userCardDTO.setId(-1);
                userCardDTO.setUsers(null);
//                userCardDTO.setMyCard(null);

            }

            OrderStatusDTO statusDTO = new OrderStatusDTO();
            OrderStatus status = order.getOrder_status();
            if (status != null) {
                statusDTO.setId(status.getId());
                statusDTO.setName(status.getName());


            } else {

                statusDTO.setId(-1);
                statusDTO.setName("");
            }

            Shipping shipping = order.getShipping();
            ShippingDTO shippingDTO = new ShippingDTO();
            if (shipping != null) {
                shippingDTO.setId(shipping.getId());
                shippingDTO.setPrice(shipping.getPrice());
                shippingDTO.setName(shipping.getName());
//                userCard.setMyCard(order.getUsers().getU);

            } else {
                shippingDTO.setId(-1);
                shippingDTO.setPrice(0);
                shippingDTO.setName("");
            }


            orderDTO.setShipping(shippingDTO);
            orderDTO.setStatus(statusDTO);
            orderDTO.setUser_card(userCardDTO);


            ///set orderDTO
            orderItemDTO.setOrder(orderDTO);

            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(orderItem.getProduct().getId());
            productDTO.setName(orderItem.getProduct().getName());
            productDTO.setPrice_origin(orderItem.getProduct().getOriginPrice());
            productDTO.setPrice_discount(orderItem.getProduct().getDiscountPrice());
            productDTO.setInventory(orderItem.getProduct().getInventory());
            productDTO.setSold_qty(orderItem.getProduct().getSoldQty());
            productDTO.setDescription(orderItem.getProduct().getDescription());
//            productDTO.setCreate_date(orderItem.getProduct().getCreate_date());
            productDTO.setView_qty(orderItem.getProduct().getView_qty());

            CategoryDTO categoryDTO = new CategoryDTO();
            Category category = orderItem.getProduct().getCategory();
            if (category == null) {
                categoryDTO.setId(-1);
                categoryDTO.setName("null");
            } else {
                categoryDTO.setId(category.getId());
                categoryDTO.setName(category.getName());
            }
//            productDTO.setCategoryDTO(categoryDTO);

            ///set productDTO
            orderItemDTO.setProduct(productDTO);
        }else
        {
            orderItemDTO=  null;
        }
        return orderItemDTO;
    }

    @Override
    public boolean addNewOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        int amount = orderItemDTO.getAmount();
        int productId = orderItemDTO.getProduct().getId();
        if (productService.confirmProdInventory(new QueueRequest(productId,amount)))
        {
            orderItem.setAmount(orderItemDTO.getAmount());
            orderItem.setSubtotal(orderItemDTO.getSubtotal());

            //set order_id
            Orders order = new Orders();
            int orderId = orderItemDTO.getOrder().getId();
            try {
                order = orderRepository.findById(orderId);
                orderItem.setOrder(order);
            } catch (Exception e) {
                System.out.println("Error in orderItemServiceImp - (addNewOrderItem) :" + e.getMessage());
                return false;
            }

//        String productName = orderItemDTO.getProduct().getName();


            try {
                Product temp = productRepository.findById(productId);
                orderItem.setProduct(temp);
            } catch (Exception e) {
                System.out.println("Error in orderItemServiceImp - (addNewOrderItem) :" + e.getMessage());
                return false;
            }

            try {
                orderItemRepository.save(orderItem);

            } catch (Exception e) {
                System.out.println("Error in OrderItemServiceImp : " + e.getMessage());
                return false ;
            }

            return true;
        }else
        {
            return false ;
        }


    }



    @Override
    public boolean updateOrderItem(int id , OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemRepository.findById(id ) ;
        if (orderItem !=null)
        {

            int amount = orderItemDTO.getAmount();
            orderItem.setAmount(amount);

            int subTotal = orderItemDTO.getSubtotal();
            orderItem.setSubtotal(subTotal);

            orderItemRepository.save(orderItem);
            return true ;
        }else
        {
            return false ;
        }
    }
}
