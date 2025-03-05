//package kr.co.pulmuone.v1.goods.item.service.goodsitem;
//
//import kr.co.pulmuone.v1.comm.base.ApiResult;
//import kr.co.pulmuone.v1.comm.base.vo.UserVo;
//import kr.co.pulmuone.v1.comm.enums.*;
//import kr.co.pulmuone.v1.comm.exception.BaseException;
//import kr.co.pulmuone.v1.comm.util.DateUtil;
//import kr.co.pulmuone.v1.comm.util.SessionUtil;
//import kr.co.pulmuone.v1.goods.item.dto.*;
//import kr.co.pulmuone.v1.goods.item.dto.vo.ItemRegistApprVo;
//import kr.co.pulmuone.v1.goods.item.dto.vo.MasterItemVo;
//import org.apache.commons.beanutils.BeanUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class _tempRegister {
//    //모든 상품 > 상품 승인 처리
//    private ApiResult<?> itemApprProc(final ItemRegisterModifyRequestDto itemRequestDto)
//            throws Exception {
//        MessageCommEnum enums = BaseEnums.Default.SUCCESS;
//        UserVo userVo = SessionUtil.getBosUserVO();
//        String userId = userVo.getUserId();					// USER ID
//        String companyType = userVo.getCompanyType();		// 회사타입
//        String clientType = userVo.getClientType();			// 거래처 타입
//
//        if(itemRequestDto.getItemApprList().isEmpty()) {
//            if(companyType.equals(CompanyEnums.CompanyType.HEADQUARTERS.getCode())) {	//관리자 권한이라면
//                itemRequestDto.setApprKindTp(ApprovalEnums.ApprovalAuthType.APPR_KIND_TP_ITEM_CLIENT.getCode());
//                ItemRegistApprVo itemApprInfo = goodsItemRegisterService.itemApprInfo(userId, itemRequestDto.getIlItemCode(), null, itemRequestDto.getApprKindTp());
//
//                if(itemApprInfo != null && itemApprInfo.getApprStat().equals(ApprovalEnums.ApprovalStatus.REQUEST.getCode())) {		//거래처 상품 수정 승인 요청 내역이 있다면
//                    enums = GoodsEnums.GoodsApprProcStatus.CLIENT_APPR_DUPLICATE;
//                }
//            }
//            else if(companyType.equals(CompanyEnums.CompanyType.CLIENT.getCode()) && clientType.equals(CompanyEnums.ClientType.CLIENT.getCode())) {	//거래처 권한이라면
//                enums = GoodsEnums.GoodsApprProcStatus.NONE_APPR;
//            }
//
//            return ApiResult.result(enums);
//        }
//
//        ItemRegistApprVo itemRegistApprVo = new ItemRegistApprVo();
//
//        itemRegistApprVo.setItemNm(itemRequestDto.getItemName());
//        itemRegistApprVo.setErpIfYn(itemRequestDto.isErpLinkIfYn());
//        itemRegistApprVo.setErpStockIfYn(itemRequestDto.isErpStockIfYn());
//        itemRegistApprVo.setIlCtgryStdId(itemRequestDto.getIlCategoryStandardId());
//        itemRegistApprVo.setUrSupplierId(itemRequestDto.getUrSupplierId());
//        itemRegistApprVo.setUrBrandId(itemRequestDto.getUrBrandId());
//        itemRegistApprVo.setStorageMethodTp(itemRequestDto.getStorageMethodType());
//        itemRegistApprVo.setOriginTp(itemRequestDto.getOriginType());
//        itemRegistApprVo.setDistributionPeriod(itemRequestDto.getDistributionPeriod());
//        itemRegistApprVo.setSizePerPackage(itemRequestDto.getSizePerPackage());
//        itemRegistApprVo.setSizeUnit(itemRequestDto.getSizeUnit());
//        itemRegistApprVo.setIlSpecMasterId(itemRequestDto.getIlSpecMasterId());
//        itemRegistApprVo.setItemGrp(itemRequestDto.getBosItemGroup());
//
//        for(ItemRegistApprRequestDto itemRegistApprRequestDto : itemRequestDto.getItemApprList()) {
//            if(itemRegistApprRequestDto.getApprManagerTp().equals(ApprovalEnums.ApprovalAuthType.APPR_MANAGER_TP_1ST.getCode())) {
//                itemRegistApprVo.setApprSubUserId(itemRegistApprRequestDto.getApprUserId());
//            }
//
//            if(itemRegistApprRequestDto.getApprManagerTp().equals(ApprovalEnums.ApprovalAuthType.APPR_MANAGER_TP_2ND.getCode())) {
//                itemRegistApprVo.setIlItemCd(itemRequestDto.getIlItemCode());
//                itemRegistApprVo.setApprKindTp(itemRegistApprRequestDto.getApprKindTp());
//                itemRegistApprVo.setApprStat(ApprovalEnums.ApprovalStatus.REQUEST.getCode());
//                itemRegistApprVo.setApprReqUserId(userId);
//                itemRegistApprVo.setApprUserId(itemRegistApprRequestDto.getApprUserId());
//            }
//        }
//
//        if(companyType.equals(CompanyEnums.CompanyType.HEADQUARTERS.getCode())) {	//관리자 권한이라면
//            itemRequestDto.setApprKindTp(ApprovalEnums.ApprovalAuthType.APPR_KIND_TP_ITEM_REG.getCode());
//            ItemRegistApprVo itemApprInfo = goodsItemRegisterService.itemApprInfo(userId, itemRequestDto.getIlItemCode(), null, itemRequestDto.getApprKindTp());
//
//            if(itemApprInfo == null || itemApprInfo.getApprStat().equals(ApprovalEnums.ApprovalStatus.DENIED.getCode())
//                    || itemApprInfo.getApprStat().equals(ApprovalEnums.ApprovalStatus.CANCEL.getCode())
//                    || itemApprInfo.getApprStat().equals(ApprovalEnums.ApprovalStatus.DISPOSAL.getCode())) {		//승인 내역이 없거나, 반려/요청철회/폐기 상태일때
//
//                goodsItemRegisterService.addItemAppr(itemRegistApprVo);
//
//                itemRegistApprVo.setPrevApprStat(ApprovalEnums.ApprovalStatus.NONE.getCode());
//                itemRegistApprVo.setStatusCmnt(null);
//
//                goodsItemRegisterService.addItemApprStatusHistory(itemRegistApprVo);
//
//            }
//            else {
//                enums = GoodsEnums.GoodsApprProcStatus.APPR_DUPLICATE;
//            }
//        }
//
//        return ApiResult.result(enums);
//    }
//
//}
