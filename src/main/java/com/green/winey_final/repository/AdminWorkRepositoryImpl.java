package com.green.winey_final.repository;

import com.green.winey_final.admin.model.*;
import com.green.winey_final.repository.support.PageCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

import static com.green.winey_final.common.entity.QAromaEntity.aromaEntity;
import static com.green.winey_final.common.entity.QCategoryEntity.categoryEntity;
import static com.green.winey_final.common.entity.QCountryEntity.countryEntity;
import static com.green.winey_final.common.entity.QFeatureEntity.featureEntity;
import static com.green.winey_final.common.entity.QOrderDetailEntity.orderDetailEntity;
import static com.green.winey_final.common.entity.QOrderEntity.orderEntity;
import static com.green.winey_final.common.entity.QProductEntity.productEntity;
import static com.green.winey_final.common.entity.QRegionNmEntity.regionNmEntity;
import static com.green.winey_final.common.entity.QSaleEntity.saleEntity;
import static com.green.winey_final.common.entity.QSmallCategoryEntity.smallCategoryEntity;
import static com.green.winey_final.common.entity.QStoreEntity.storeEntity;
import static com.green.winey_final.common.entity.QUserEntity.userEntity;
import static com.green.winey_final.common.entity.QWinePairingEntity.winePairingEntity;

@Repository
@RequiredArgsConstructor
public class AdminWorkRepositoryImpl implements AdminQdslRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public PageCustom<ProductVo> selProductAll(Pageable pageable, String str) {

        BooleanBuilder whereBuilder = new BooleanBuilder();
        if(str != null) {
            whereBuilder.and(productEntity.nmKor.contains(str));
        }

        List<ProductVo> list = queryFactory.select(new QProductVo(productEntity.productId, productEntity.nmKor, productEntity.price, productEntity.promotion, productEntity.beginner, productEntity.quantity, saleEntity.sale, saleEntity.salePrice))
                .from(productEntity)
                .leftJoin(saleEntity)
                .on(saleEntity.productEntity.eq(productEntity))
                .orderBy(getAllOrderSpecifiers(pageable))
                .where(whereBuilder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(productEntity.count())
                .from(productEntity)
                .leftJoin(saleEntity)
                .on(saleEntity.productEntity.eq(productEntity));

        Page<ProductVo> map = PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);

        return new PageCustom<ProductVo>(map.getContent(), map.getPageable(), map.getTotalElements());
    }

    @Override
    public PageCustom<UserVo> selUserAll(Pageable pageable, String searchType, String str) {

        List<UserVo> list = queryFactory.select(new QUserVo(userEntity.userId, userEntity.email, userEntity.unm, regionNmEntity.regionNmId.intValue(), userEntity.createdAt.stringValue()))
                .from(userEntity)
                .where(eqUserName(searchType, str),
        eqUserEmail(searchType, str), userEntity.delYn.eq(0L))
                .orderBy(getAllOrderSpecifiers(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
//                .select(userEntity.count())// count()와 countDistinct() 차이 알기
                .select(userEntity.countDistinct())// count()와 countDistinct() 차이 알기
                .from(userEntity)
                .where(eqUserName(searchType, str),
                        eqUserEmail(searchType, str), userEntity.delYn.eq(0L));

        Page<UserVo> map = PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);

        return new PageCustom<UserVo>(map.getContent(), map.getPageable(), map.getTotalElements());
    }

    @Override
    public PageCustom<UserOrderDetailVo> selUserOrderByUserId(Long userId, Pageable pageable) {
        //데이트포맷 로직
        //        StringTemplate formattedDate = Expressions.dateTemplate(
//                "DATE_FORMAT({0}, {1})",
//                UserOrderDetailVo,
//                ConstantImpl.create("%y-%m-%d"));

        List<UserOrderDetailVo> list = queryFactory
                .select(new QUserOrderDetailVo(orderEntity.orderId, orderEntity.orderDate.stringValue(), productEntity.nmKor, orderEntity.totalOrderPrice.intValue(), storeEntity.nm, orderEntity.orderStatus.intValue(), orderDetailEntity.count().intValue()))
                .from(userEntity)
                .innerJoin(orderEntity)
                .on(userEntity.eq(orderEntity.userEntity))
                .innerJoin(orderDetailEntity)
                .on(orderDetailEntity.orderEntity.eq(orderEntity))
                .innerJoin(productEntity)
                .on(productEntity.eq(orderDetailEntity.productEntity))
                .innerJoin(storeEntity)
                .on(orderEntity.storeEntity.eq(storeEntity))
                .where(userEntity.userId.eq(userId))
                .groupBy(orderEntity.orderId)
                .orderBy(getAllOrderSpecifiers(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

//        LocalDate now = LocalDate.now();
//        int year = now.getYear();
//        int startMonth = now.getMonthValue();
//
//        LocalDate startOfMonth = LocalDate.of(year, startMonth, 1); // 이번 달의 시작일
//        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);

        for(int i=0;i<list.size();i++) {
            if(list.get(i).getCount()>1) {
                list.get(i).setNmKor(list.get(i).getNmKor()+" 외 "+(list.get(i).getCount()-1));
            }
        }


        JPAQuery<Long> countQuery = queryFactory
//                .select(userEntity.userId.countDistinct())// count()와 countDistinct() 차이 알기
                .select(userEntity.userId.count())// count()와 countDistinct() 차이 알기
                .from(userEntity)
                .innerJoin(orderEntity)
                .on(userEntity.userId.eq(orderEntity.orderId))
                .innerJoin(orderDetailEntity)
                .on(orderEntity.orderId.eq(orderDetailEntity.orderEntity.orderId))
                .innerJoin(productEntity)
                .on(productEntity.productId.eq(orderDetailEntity.productEntity.productId))
                .innerJoin(storeEntity)
                .on(orderEntity.storeEntity.storeId.eq(storeEntity.storeId))
//                .where(userEntity.userId.eq(userId)) //이 부분 주석 풀면 안됨... 이유 찾아야함
                .groupBy(orderEntity.orderId);


        Page<UserOrderDetailVo> map = PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);

        return new PageCustom<UserOrderDetailVo>(map.getContent(), map.getPageable(), map.getTotalElements());
    }

    @Override
    public UserInfo selUserInfoByUserId(Long userId, Pageable pageable) {
        UserInfo user = queryFactory.select(new QUserInfo(userEntity.userId, userEntity.email, userEntity.unm,
                        ExpressionUtils.as(orderEntity.totalOrderPrice.sum().intValue(), "sumOrderPrice"),
                        ExpressionUtils.as(orderEntity.orderId.count().intValue(), "orderCount") //ExpressionUtils.as(JPAExpressions.select(count(orderEntity.orderId)).from(orderEntity),"orderCount")
                ))
                .from(userEntity)
                .innerJoin(orderEntity)
                .on(userEntity.eq(orderEntity.userEntity))
                .where(userEntity.userId.eq(userId))
                .fetchOne();

        return user;
    }

    @Override
    public PageCustom<OrderListVo> selOrderAll(Pageable pageable) {
        List<OrderListVo> list = queryFactory
                .select(new QOrderListVo(orderEntity.orderId, orderEntity.orderDate.stringValue(), userEntity.email, productEntity.nmKor,
                        orderDetailEntity.quantity.sum().intValue(),
                        orderDetailEntity.salePrice.sum().intValue(),
                        orderEntity.totalOrderPrice.intValue(),
                        orderEntity.payment.intValue(), storeEntity.nm,
                        orderEntity.orderStatus.intValue(), orderDetailEntity.count().intValue()))
                .from(orderEntity)
                .innerJoin(userEntity)
                .on(orderEntity.userEntity.eq(userEntity))
                .join(storeEntity)
                .on(orderEntity.storeEntity.eq(storeEntity))
                .join(orderDetailEntity)
                .on(orderEntity.eq(orderDetailEntity.orderEntity))
                .join(productEntity)
                .on(orderDetailEntity.productEntity.eq(productEntity))
                .groupBy(orderEntity.orderId)
                .orderBy(getAllOrderSpecifiers(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        for(int i=0;i<list.size();i++) {
            if(list.get(i).getCount()>1) {
                list.get(i).setNmKor(list.get(i).getNmKor()+" 외 "+(list.get(i).getCount()-1));
            }
        }

        JPAQuery<Long> countQuery = queryFactory
                .select(orderEntity.orderId.count())
//                .select(orderEntity.orderId.countDistinct())
                .from(orderEntity)
                .innerJoin(userEntity)
                .on(orderEntity.userEntity.eq(userEntity))
                .join(storeEntity)
                .on(orderEntity.storeEntity.eq(storeEntity))
                .join(orderDetailEntity)
                .on(orderEntity.eq(orderDetailEntity.orderEntity))
                .join(productEntity)
                .on(orderDetailEntity.productEntity.eq(productEntity));
//                .groupBy(orderEntity.orderId); //groupBy하면 totalElements 제대로 안나옴

        Page<OrderListVo> map = PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);

        return new PageCustom<OrderListVo>(map.getContent(), map.getPageable(), map.getTotalElements());
    }

    @Override
    public PageCustom<StoreVo> selStoreAll(Pageable pageable, String searchType, String str) {
        List<StoreVo> list = queryFactory.select(new QStoreVo(storeEntity.storeId, regionNmEntity.regionNmId, storeEntity.nm, storeEntity.tel, storeEntity.address))
                .from(storeEntity)
                .where(eqStoreNm(searchType, str),
                        eqStoreAddr(searchType, str),
                        eqStoreTel(searchType, str))
                .orderBy(getAllOrderSpecifiers(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(storeEntity.count())
                .from(storeEntity);

        Page<StoreVo> map = PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);

        return new PageCustom<StoreVo>(map.getContent(), map.getPageable(), map.getTotalElements());
    }

    //상세 주문 내역1
    @Override
    public List<OrderDetail1> selOrderDetailByOrderId(int orderId, Pageable pageable) {
        List<OrderDetail1> list = queryFactory
                .select(new QOrderDetail1(orderEntity.orderId, orderEntity.orderDate.stringValue(), userEntity.email, productEntity.nmKor, orderDetailEntity.salePrice, orderDetailEntity.quantity))
                .from(orderEntity)
                .innerJoin(orderDetailEntity)
                .on(orderEntity.eq(orderDetailEntity.orderEntity))
                .innerJoin(userEntity)
                .on(orderEntity.userEntity.userId.eq(userEntity.userId))
                .innerJoin(productEntity)
                .on(orderDetailEntity.productEntity.eq(productEntity))
                .where(orderEntity.orderId.eq((long) orderId))
                .fetch();

        return list;
/* 추후 페이징 필요하면 사용
        JPAQuery<Long> countQuery = queryFactory
                .select(orderEntity.orderId.count())
                .from(orderEntity)
                .innerJoin(orderDetailEntity)
                .on(orderEntity.eq(orderDetailEntity.orderEntity))
                .innerJoin(userEntity)
                .on(orderEntity.userEntity.userId.eq(userEntity.userId))
                .innerJoin(productEntity)
                .on(orderDetailEntity.productEntity.eq(productEntity))
                .where(orderEntity.orderId.eq((long) orderId));

        Page<OrderDetail1> map = PageableExecutionUtils.getPage(list, pageable, countQuery::fetchOne);

        return new PageCustom<OrderDetail1>(map.getContent(), map.getPageable(), map.getTotalElements());
 */
    }

    //상세 주문 내역2
    @Override
    public OrderDetail2 selOrderDetailByOrderId2(int orderId, Pageable pageable) {
        OrderDetail2 list = queryFactory
                .select(new QOrderDetail2(orderDetailEntity.quantity.sum(), orderDetailEntity.salePrice.sum(), orderEntity.totalOrderPrice, orderEntity.payment.stringValue(), storeEntity.nm, orderEntity.pickupTime.stringValue(), orderEntity.orderStatus))
                .from(orderEntity)
                .innerJoin(orderDetailEntity)
                .on(orderEntity.eq(orderDetailEntity.orderEntity))
                .innerJoin(storeEntity)
                .on(orderEntity.storeEntity.eq(storeEntity))
                .where(orderEntity.orderId.eq((long) orderId))
                .fetchOne();

        return list;
    }

    //상품수정용 상품디테일1
    @Override
    public AdminProductDetailVo selPutProductInfo1(int productId) {
        AdminProductDetailVo product = queryFactory
                .select(new QAdminProductDetailVo(productEntity.productId.intValue(), productEntity.nmKor, productEntity.nmEng, productEntity.price, productEntity.promotion, productEntity.beginner, productEntity.alcohol, productEntity.quantity, productEntity.pic,
                        countryEntity.countryId.intValue(), featureEntity.sweety.intValue(), featureEntity.acidity.intValue(), featureEntity.body.intValue(), categoryEntity.categoryId.intValue(), saleEntity.sale, saleEntity.salePrice, saleEntity.startSale, saleEntity.endSale, saleEntity.saleYn))
                .from(productEntity)
                .innerJoin(saleEntity)
                .on(productEntity.eq(saleEntity.productEntity))
                .innerJoin(featureEntity)
                .on(productEntity.featureEntity.eq(featureEntity))
                .where(productEntity.productId.eq((long) productId))
                .fetchOne();

        return product;
    }
    //상품수정용 상품디테일2 아로마
    @Override
    public List<Long> selPutProductInfo2(int productId) {
        List<Long> aroma = queryFactory
                .select(aromaEntity.aromaCategoryEntity.aromaCategoryId)
                .from(aromaEntity)
                .where(aromaEntity.productEntity.productId.eq((long) productId))
                .fetch();
        return aroma;
    }
    //상품수정용 상품디테일3 smallCategoryId 음식
    @Override
    public List<Long> selPutProductInfo3(int productId) {
        List<Long> smallCategoryId = queryFactory
                .select(smallCategoryEntity.smallCategoryId)
                .from(winePairingEntity)
                .where(winePairingEntity.productEntity.productId.eq((long) productId))
                .fetch();
        return smallCategoryId;
    }



    //정렬
    private OrderSpecifier[] getAllOrderSpecifiers(Pageable pageable) {
//        List<OrderSpecifier> orders = new ArrayList();
        List<OrderSpecifier> orders = new LinkedList<>();
        if(!pageable.getSort().isEmpty()) {
            for(Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                String str=order.getProperty();
                //order의 property값이 스웨거 입력칸 sort의 number
                switch (order.getProperty().toLowerCase()) {
                    //등록 상품 정렬
                    case "productid": orders.add(new OrderSpecifier(direction, productEntity.productId)); break;
                    case "saleprice": orders.add(new OrderSpecifier(direction, saleEntity.salePrice)); break;
                    case "sale": orders.add(new OrderSpecifier(direction, saleEntity.sale)); break;
                    case "price": orders.add(new OrderSpecifier(direction, productEntity.price)); break;
                    case "recommend":
                        orders.add(new OrderSpecifier(direction, productEntity.promotion));
                        orders.add(new OrderSpecifier(direction, productEntity.beginner)); break; //
                    case "quantity": orders.add(new OrderSpecifier(direction, productEntity.quantity)); break;

                    //가입 회원 리스트 정렬
                    case "userid": orders.add(new OrderSpecifier(direction, userEntity.userId)); break;
                    case "pickup": orders.add(new OrderSpecifier(direction, userEntity.regionNmEntity.regionNmId)); break;

                    //회원별 상세 주문 내역 정렬
                    case "orderid": orders.add(new OrderSpecifier(direction, orderEntity.orderId)); break;
                    case "orderdate": orders.add(new OrderSpecifier(direction, orderEntity.orderDate)); break;
//                    case "storenm": orders.add(new OrderSpecifier(direction, storeEntity.regionNmEntity)); break;
                    case "orderstatus": orders.add(new OrderSpecifier(direction, orderEntity.orderStatus)); break;

                    //주문 내역 정렬
//                    case "orderid": orders.add(new OrderSpecifier(direction, orderEntity.orderId)); break;
//                    case "orderdate": orders.add(new OrderSpecifier(direction, orderEntity.orderDate)); break;
//                    case "storenm": orders.add(new OrderSpecifier(direction, regionNmEntity.regionNm)); break;
//                    case "orderstatus": orders.add(new OrderSpecifier(direction, orderEntity.orderStatus)); break;
                    case "orderdatemonth": orders.add(new OrderSpecifier(direction, orderEntity.orderDate)); break;


                    //매장 정렬
                    case "storeid": orders.add(new OrderSpecifier(direction, storeEntity.storeId)); break; //주문내역과 공동
                    case "storenm": orders.add(new OrderSpecifier(direction, storeEntity.nm)); break; //주문내역과 공동
                    case "address": orders.add(new OrderSpecifier(direction, storeEntity.address)); break;
                    case "storetel": orders.add(new OrderSpecifier(direction, storeEntity.tel)); break;


                }
            }
        }
        return orders.stream().toArray(OrderSpecifier[]::new);
    }

    //동적 검색 조건
    /*
    equalsIgnoreCase() 대소문자 구분없이 비교
    equals() 대소문자 구분해서 비교
     */
    //유저 검색 조건
    public BooleanExpression eqUserName(String searchType, String str) {
        if (searchType == null) {
            return null;
        } else if (searchType.equalsIgnoreCase("unm")) {
            return userEntity.unm.containsIgnoreCase(str);
        }
        return null;

    }

    public BooleanExpression eqUserEmail(String searchType, String str) {
        if (searchType == null) {
            return null;
        } else if (searchType.equalsIgnoreCase("email")) {
            return userEntity.email.containsIgnoreCase(str);
        }
        return null;

    }

    //

    public BooleanExpression eqStoreNm(String searchType, String str) {
        if (searchType == null) {
            return null;
        } else if (searchType.equalsIgnoreCase("storename")) {
            return storeEntity.nm.containsIgnoreCase(str);
        }
        return null;
    }

    public BooleanExpression eqStoreAddr(String searchType, String str) {
        if (searchType == null) {
            return null;
        } else if (searchType.equalsIgnoreCase("storeaddress")) {
            return storeEntity.address.containsIgnoreCase(str);
        }
        return null;
    }

    public BooleanExpression eqStoreTel(String searchType, String str) {
        if (searchType == null) {
            return null;
        } else if (searchType.equalsIgnoreCase("storetel")) {
            return storeEntity.tel.containsIgnoreCase(str);
        }
        return null;
    }



}