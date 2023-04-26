package cybersoft.java20.dev3lopers.gear3sproject.service;

import cybersoft.java20.dev3lopers.gear3sproject.dto.*;
import cybersoft.java20.dev3lopers.gear3sproject.entity.*;
import cybersoft.java20.dev3lopers.gear3sproject.dto.CountOrdersGroupByMonthDTO;
import cybersoft.java20.dev3lopers.gear3sproject.dto.CountOrdersGroupByYearDTO;
import cybersoft.java20.dev3lopers.gear3sproject.repository.*;
import cybersoft.java20.dev3lopers.gear3sproject.service.imp.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserCardRepository userCardRepository;

    @Autowired
    ShippingRepository shippingRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MyCardRepository myCardRepository;



    @Override
    public List<OrderDTO> findAll() {
        List<Orders> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Orders order : orders
        ) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setId(order.getId());
            orderDTO.setOrderDate(order.getOrderDate());
            orderDTO.setTotal(order.getTotal());
            orderDTO.setShippingAddress(order.getShippingAddress());


            UserCardDTO userCardDTO = new UserCardDTO();
            UserCard userCard = order.getUser_card();
            if (userCard != null) {
                userCardDTO.setId(userCard.getId());

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

            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    @Override
    public boolean insertNewOrder(OrderDTO orderDTO) {
        Orders order = new Orders();

//        order.setId(orderDTO.getId());
        order.setOrderDate(orderDTO.getOrderDate());
        order.setTotal(orderDTO.getTotal());
        order.setShippingAddress(orderDTO.getShippingAddress());


        UserCard userCard = new UserCard();
        try {

//            String phone = orderDTO.getUser_card().getUsers().getPhone();
//            List<Users> users = userRepository.findUserByPhone((phone));
//            Users user = users.get(0);
//
//            String number = orderDTO.getUser_card().getMyCard().getNumber();
//
//
//            MyCard myCard = myCardRepository.findByNumber((number));
//
//            int uId = user.getId();
//            int mId = myCard.getId();
            int uid = orderDTO.getUser_card().getUsers().getId();
            int mid = orderDTO.getUser_card().getMyCard().getId();
            userCard = userCardRepository.findByUserIdAndMycardId(uid,mid);
            System.out.println("userCard null ? : " + (userCard == null ? true : false));
//            System.out.println("uid : "+uId+", mid : "+mId);

        } catch (Exception e) {
            System.out.println("Error orderServiceImp : " + e.getMessage());
            userCard = null;
            return false;
        }

        OrderStatus status = new OrderStatus();
        try {

            String name = orderDTO.getStatus().getName();

            status = orderStatusRepository.findByNameLike((name));
        } catch (Exception e) {
            System.out.println("Error orderServiceImp : " + e.getMessage());
            status = null;
            return false;
        }


        Shipping shipping = new Shipping();
        try {
            String name = orderDTO.getShipping().getName();
            shipping = shippingRepository.findByNameLike((name));
        } catch (Exception e) {
            System.out.println("Error orderServiceImp : " + e.getMessage());
            shipping = null;
            return false;

        }


        order.setShipping(shipping);
        order.setOrder_status(status);
        order.setUser_card(userCard);

        try
        {
            orderRepository.save(order);


        }catch (Exception e )
        {
            System.out.println("Error in orderServiceImp : "+e.getMessage());
            return false ;

        }



        return true;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            orderRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }


    }


    @Override
    public boolean updateById(int id, OrderDTO orderDTO) {
        Orders order = null;
        order = orderRepository.findById(id);
        if (order != null) {
            //change ship address

            String address = orderDTO.getShippingAddress();
            if (address != null) {
                order.setShippingAddress(address);
            } else {
                address = order.getShippingAddress();
                order.setShippingAddress(address);
            }




            //change order date

            Date date = orderDTO.getOrderDate();
            if (date != null) {
                order.setOrderDate(date);
            } else {
                date = order.getOrderDate();
                order.setOrderDate(date);
            }


            //change total
            long total = orderDTO.getTotal();
            if (total != 0) {
                order.setTotal(total);
            } else {
                total = order.getTotal();
                order.setTotal(total);
            }


            //change shipping id
            try {
                ShippingDTO shippingDTO = orderDTO.getShipping();
                String shipName = shippingDTO.getName();
                System.out.println("Shipname :" + shipName);
                Shipping shipping = shippingRepository.findByNameLike((shipName));
                order.setShipping(shipping);
                System.out.println("Shipping null ? : " + (shipping == null ? true : false));


            } catch (Exception e) {
                Shipping shipping = order.getShipping();
                order.setShipping(shipping);
                System.out.println("Shipping null ? : " + (shipping == null ? true : false));
            }

            //change status id
            try {
                OrderStatusDTO orderStatusDTO = orderDTO.getStatus();
                String name = orderStatusDTO.getName();
                System.out.println("Status :" + name);

                OrderStatus status = orderStatusRepository.findByNameLike((name));

                order.setOrder_status(status);
                System.out.println("status null ? : " + (status == null ? true : false));

            } catch (Exception e) {
                OrderStatus status = order.getOrder_status();

                order.setOrder_status(status);
                System.out.println("status null ? : " + (status == null ? true : false));
            }

            //change user_card id
            UserCard userCard = new UserCard();
            try {
//                String phone = orderDTO.getUser_card().getUsers().getPhone();
//                List<Users> users = userRepository.findUserByPhone(phone);
//                Users user = users.get(0);
//
//                String number = orderDTO.getUser_card().getMyCard().getNumber();
//
//
//                MyCard myCard = myCardRepository.findByNumber((number));

                int uId = orderDTO.getUser_card().getUsers().getId();
                int mId = orderDTO.getUser_card().getMyCard().getId();
                userCard = userCardRepository.findByUserIdAndMycardId(uId, mId);
                System.out.println("userCard null ? : " + (userCard == null ? true : false));
                order.setUser_card(userCard);

//            System.out.println("uid : "+uId+", mid : "+mId);
//                deleteById(id);

            } catch (Exception e) {
                userCard = order.getUser_card();
                order.setUser_card(userCard);
            }

            orderRepository.save(order);
            return true;
        } else {
            insertNewOrder(orderDTO);
            return true;
        }
    }

    @Override
    public OrderDTO findById(int id) {
        Orders order = orderRepository.findById(id);
        OrderDTO orderDTO = new OrderDTO();
        if (order!=null)
        {
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
        }else
        {
            orderDTO= null;
        }


        return  orderDTO;
    }

    @Override
    public List<CountOrdersGroupByYearDTO> countOrdersGroupByYear( ) {
        LocalDate currentdate = LocalDate.now();
        int  year = currentdate.getYear();
        return orderRepository.countOrdersGroupByYear(year);
    }

    public List<CountOrdersGroupByMonthDTO> countOrdersGroupByMonth( ) {
        LocalDate currentdate = LocalDate.now();
        int  month = currentdate.getMonth().getValue();
        return orderRepository.countOrdersGroupByMonth(month);
    }

    @Override
    public List<SumTotalOrdersGroupByYearDTO> sumOrdersGroupByYear( ) {
        LocalDate currentdate = LocalDate.now();
        int  year = currentdate.getYear();
        return orderRepository.sumOrdersGroupByYear(year);
    }

    @Override
    public List<SumTotalOrdersGroupByMonthDTO> sumOrdersGroupByMonth( ) {
        LocalDate currentdate = LocalDate.now();
        int  month = currentdate.getMonth().getValue();
        return orderRepository.sumOrdersGroupByMonth(month);
    }

    @Override
    public List<SumTotalOrdersGroupByMonthDTO> sumOrdersGroupByAllMonths( ) {
        List<SumTotalOrdersGroupByMonthDTO> finalList = new ArrayList<>();
        List<SumTotalOrdersGroupByMonthDTO> existed = orderRepository.sumOrdersGroupByExistedMonth();
        for (int i = 1; i <=12 ; i++) {
            SumTotalOrdersGroupByMonthDTO sumTotalOrdersGroupByMonthDTO= new SumTotalOrdersGroupByMonthDTO();
            sumTotalOrdersGroupByMonthDTO.setMonth(i);
            for (SumTotalOrdersGroupByMonthDTO item: existed
                 ) {
                if (item.getMonth() == i)
                {
                    sumTotalOrdersGroupByMonthDTO.setRevenue(item.getRevenue());
                    break;
                }

                    sumTotalOrdersGroupByMonthDTO.setRevenue(0);


            }
            finalList.add(sumTotalOrdersGroupByMonthDTO);
        }
        return finalList;
    }

}
