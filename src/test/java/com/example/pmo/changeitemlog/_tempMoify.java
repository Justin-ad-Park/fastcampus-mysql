//package kr.co.pulmuone.v1.goods.item.service.goodsitem;
//
//import kr.co.pulmuone.v1.comm.base.ApiResult;
//import kr.co.pulmuone.v1.comm.base.vo.UserVo;
//import kr.co.pulmuone.v1.comm.enums.*;
//import kr.co.pulmuone.v1.comm.util.DateUtil;
//import kr.co.pulmuone.v1.comm.util.SessionUtil;
//import kr.co.pulmuone.v1.goods.item.dto.ItemCertificationDto;
//import kr.co.pulmuone.v1.goods.item.dto.ItemNutritionDetailDto;
//import kr.co.pulmuone.v1.goods.item.dto.ItemRegistApprRequestDto;
//import kr.co.pulmuone.v1.goods.item.dto.ItemSpecValueRequestDto;
//import kr.co.pulmuone.v1.goods.item.dto.vo.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class _tempMoify {
//    //모든 상품 > 상품 승인 처리
//    private ApiResult<?> itemApprProc(ItemRegisterModifyRequestDto itemRequestDto, MasterItemVo beforeItemDetail, List<ItemCertificationListVo> beforeItemCertList
//            , List<ItemImageVo> beforeItemImageList, List<ItemNutritionDetailVo> beforeItemNurList, List<ItemSpecValueVo> beforeItemSpecList) throws Exception {
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
//
//            return ApiResult.result(enums);
//        }
//
//
//        if(companyType.equals(CompanyEnums.CompanyType.CLIENT.getCode()) && clientType.equals(CompanyEnums.ClientType.CLIENT.getCode())) {	//거래처 권한이라면
//            itemRequestDto.setApprKindTp(ApprovalEnums.ApprovalAuthType.APPR_KIND_TP_GOODS_CLIENT.getCode());
//            ItemRegistApprVo itemApprInfo = goodsItemRegisterService.itemApprInfo(userId, itemRequestDto.getIlItemCode(), null, itemRequestDto.getApprKindTp());
//
//            int differentCount = 0;																//거래처 상품 변경 카운트
//
//            if(beforeItemDetail != null) {
//
//                itemRegistApprVo.setItemNm(itemRequestDto.getItemName());
//                itemRegistApprVo.setErpIfYn(itemRequestDto.isErpLinkIfYn());
//                itemRegistApprVo.setErpStockIfYn(itemRequestDto.isErpStockIfYn());
//                itemRegistApprVo.setIlCtgryStdId(itemRequestDto.getIlCategoryStandardId());
//                itemRegistApprVo.setUrSupplierId(itemRequestDto.getUrSupplierId());
//
//                int dateCompare = DateUtil.string2Date(beforeItemDetail.getModifyDt(), "yyyy-MM-dd HH:mm:ss").compareTo(DateUtil.string2Date(itemRequestDto.getLoadDateTime(), "yyyy-MM-dd HH:mm:ss"));
//
//                if(dateCompare > 0) {
//                    enums = GoodsEnums.GoodsApprProcStatus.ADMIN_DIFFERENT_ITEM;
//                    itemRequestDto.setLoadDateTime(null);
//                }
//                else {
//                    // 상품군
//                    if (
//                            (beforeItemDetail.getBosItemGroup() == null && itemRequestDto.getBosItemGroup() != null)
//                                    || (beforeItemDetail.getBosItemGroup() != null && !beforeItemDetail.getBosItemGroup().equals(itemRequestDto.getBosItemGroup()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setItemGrp(itemRequestDto.getBosItemGroup());
//
//                    // 보관방법
//                    if (
//                            (beforeItemDetail.getStorageMethodType() == null && itemRequestDto.getStorageMethodType() != null)
//                                    || (beforeItemDetail.getStorageMethodType() != null && !beforeItemDetail.getStorageMethodType().equals(itemRequestDto.getStorageMethodType()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setStorageMethodTp(itemRequestDto.getStorageMethodType());
//
//                    // 원산지
//                    if (
//                            (beforeItemDetail.getOriginType() == null && itemRequestDto.getOriginType() != null)
//                                    || (beforeItemDetail.getOriginType() != null && !beforeItemDetail.getOriginType().equals(itemRequestDto.getOriginType()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setOriginTp(itemRequestDto.getOriginType());
//
//                    // 원산지 상세
//                    if (
//                            (beforeItemDetail.getOriginDetail() == null && itemRequestDto.getOriginDetail() != null)
//                                    || (beforeItemDetail.getOriginDetail() != null && !beforeItemDetail.getOriginDetail().equals(itemRequestDto.getOriginDetail()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setOriginDetl(itemRequestDto.getOriginDetail());
//
//                    // 박스체적 - 가로
//                    if (
//                            (beforeItemDetail.getBoxWidth() == null && itemRequestDto.getBoxWidth() != null)
//                                    || (beforeItemDetail.getBoxWidth() != null && !beforeItemDetail.getBoxWidth().equals(itemRequestDto.getBoxWidth()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setBoxWidth(itemRequestDto.getBoxWidth());
//
//                    // 박스체적 - 세로
//                    if (
//                            (beforeItemDetail.getBoxDepth() == null && itemRequestDto.getBoxDepth() != null)
//                                    || (beforeItemDetail.getBoxDepth() != null && !beforeItemDetail.getBoxDepth().equals(itemRequestDto.getBoxDepth()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setBoxDepth(itemRequestDto.getBoxDepth());
//
//                    // 박스체적 - 높이
//                    if (
//                            (beforeItemDetail.getBoxHeight() == null && itemRequestDto.getBoxHeight() != null)
//                                    || (beforeItemDetail.getBoxHeight() != null && !beforeItemDetail.getBoxHeight().equals(itemRequestDto.getBoxHeight()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setBoxHeight(itemRequestDto.getBoxHeight());
//
//                    // 박스입수량
//                    if(beforeItemDetail.getPiecesPerBox() != itemRequestDto.getPiecesPerBox()) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setPiecesPerBox(itemRequestDto.getPiecesPerBox());
//
//                    // UOM
//                    if (
//                            (beforeItemDetail.getUnitOfMeasurement() == null && itemRequestDto.getUnitOfMeasurement() != null)
//                                    || (beforeItemDetail.getUnitOfMeasurement() != null && !beforeItemDetail.getUnitOfMeasurement().equals(itemRequestDto.getUnitOfMeasurement()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setOms(String.valueOf(itemRequestDto.getUnitOfMeasurement()));
//
//                    // 유통기간
//                    if(beforeItemDetail.getDistributionPeriod() != itemRequestDto.getDistributionPeriod()) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setDistributionPeriod(itemRequestDto.getDistributionPeriod());
//
//                    // 포장단위별 용량
//                    if (
//                            (beforeItemDetail.getSizePerPackage() == null && itemRequestDto.getSizePerPackage() != null)
//                                    || (beforeItemDetail.getSizePerPackage() != null && !beforeItemDetail.getSizePerPackage().equals(itemRequestDto.getSizePerPackage()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setSizePerPackage(itemRequestDto.getSizePerPackage());
//
//                    // 용량(중량) 단위
//                    if (
//                            (beforeItemDetail.getSizeUnit() == null && itemRequestDto.getSizeUnit() != null)
//                                    || (beforeItemDetail.getSizeUnit() != null && !beforeItemDetail.getSizeUnit().equals(itemRequestDto.getSizeUnit()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setSizeUnit(itemRequestDto.getSizeUnit());
//
//                    // 용량(중량) 단위 - 직접입력
//                    if (
//                            (beforeItemDetail.getSizeUnitEtc() == null && itemRequestDto.getSizeUnitEtc() != null)
//                                    || (beforeItemDetail.getSizeUnitEtc() != null && !beforeItemDetail.getSizeUnitEtc().equals(itemRequestDto.getSizeUnitEtc()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setSizeUnitEtc(itemRequestDto.getSizeUnitEtc());
//
//                    // 포장구성 수량
//                    if (
//                            (beforeItemDetail.getQuantityPerPackage() == null && itemRequestDto.getQuantityPerPackage() != null)
//                                    || (beforeItemDetail.getQuantityPerPackage() != null && !beforeItemDetail.getQuantityPerPackage().equals(itemRequestDto.getQuantityPerPackage()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setQtyPerPackage(itemRequestDto.getQuantityPerPackage());
//
//                    // 포장구성 단위
//                    if (
//                            (beforeItemDetail.getPackageUnit() == null && itemRequestDto.getPackageUnit() != null)
//                                    || (beforeItemDetail.getPackageUnit() != null && !beforeItemDetail.getPackageUnit().equals(itemRequestDto.getPackageUnit()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setPackageUnit(itemRequestDto.getPackageUnit());
//
//                    // 포장구성 단위 - 직접입력
//                    if (
//                            (beforeItemDetail.getPackageUnitEtc() == null && itemRequestDto.getPackageUnitEtc() != null)
//                                    || (beforeItemDetail.getPackageUnitEtc() != null && !beforeItemDetail.getPackageUnitEtc().equals(itemRequestDto.getPackageUnitEtc()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setPackageUnitEtc(itemRequestDto.getPackageUnitEtc());
//
//                    // 표준브랜드
//                    if (
//                            (beforeItemDetail.getUrBrandId() == null && itemRequestDto.getUrBrandId() != null)
//                                    || (beforeItemDetail.getUrBrandId() != null && !beforeItemDetail.getUrBrandId().equals(itemRequestDto.getUrBrandId()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setUrBrandId(itemRequestDto.getUrBrandId());
//
//                    // 전시 브랜드
//                    if (
//                            (beforeItemDetail.getDpBrandId() == null && itemRequestDto.getDpBrandId() != null)
//                                    || (beforeItemDetail.getDpBrandId() != null && !beforeItemDetail.getDpBrandId().equals(itemRequestDto.getDpBrandId()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setDpBrandId(itemRequestDto.getDpBrandId());
//
//                    // 상품정보제공
//                    if (
//                            beforeItemDetail.getIlSpecMasterId() == null
//                                    || (beforeItemDetail.getIlSpecMasterId() != null && !beforeItemDetail.getIlSpecMasterId().equals(itemRequestDto.getIlSpecMasterId()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setIlSpecMasterId(itemRequestDto.getIlSpecMasterId());
//
//                    // 영양정보 표시여부
//                    if(beforeItemDetail.isNutritionDisplayYn() != itemRequestDto.isNutritionDisplayYn() ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setNutritionDispYn(itemRequestDto.isNutritionDisplayYn());
//
//                    // 영양정보 표시 기본
//                    if (
//                            (beforeItemDetail.getNutritionDisplayDefalut() == null && itemRequestDto.getNutritionDisplayDefalut() != null)
//                                    || (beforeItemDetail.getNutritionDisplayDefalut() != null && !beforeItemDetail.getNutritionDisplayDefalut().equals(itemRequestDto.getNutritionDisplayDefalut()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setNutritionDispDefault(itemRequestDto.getNutritionDisplayDefalut());
//
//                    // 영양분석 단위 1회 분량
//                    if (
//                            (beforeItemDetail.getNutritionQuantityPerOnce() == null && itemRequestDto.getNutritionQuantityPerOnce() != null)
//                                    || (beforeItemDetail.getNutritionQuantityPerOnce() != null && !beforeItemDetail.getNutritionQuantityPerOnce().equals(itemRequestDto.getNutritionQuantityPerOnce()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setNutritionQtyPerOnce(itemRequestDto.getNutritionQuantityPerOnce());
//
//                    // 영양분석 단위 총 분량
//                    if (
//                            (beforeItemDetail.getNutritionQuantityTotal() == null && itemRequestDto.getNutritionQuantityTotal() != null)
//                                    || (beforeItemDetail.getNutritionQuantityTotal() != null && !beforeItemDetail.getNutritionQuantityTotal().equals(itemRequestDto.getNutritionQuantityTotal()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setNutritionQtyTotal(itemRequestDto.getNutritionQuantityTotal());
//
//                    // 영양성분 기타
//                    if (
//                            (beforeItemDetail.getNutritionEtc() == null && itemRequestDto.getNutritionEtc() != null)
//                                    || (beforeItemDetail.getNutritionEtc() != null && !beforeItemDetail.getNutritionEtc().equals(itemRequestDto.getNutritionEtc()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setNutritionEtc(itemRequestDto.getNutritionEtc());
//
//                    // 동영상 URL
//                    if (
//                            (beforeItemDetail.getVideoUrl() == null && itemRequestDto.getVideoUrl() != null)
//                                    || (beforeItemDetail.getVideoUrl() != null && !beforeItemDetail.getVideoUrl().equals(itemRequestDto.getVideoUrl()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setVideoUrl(itemRequestDto.getVideoUrl());
//
//                    // 동영상 자동재생여부
//                    if(beforeItemDetail.isVideoAutoplayYn() != itemRequestDto.isVideoAutoplayYn() ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setVideoAutoplayYn(itemRequestDto.isVideoAutoplayYn());
//
//                    // 상품상세 기본 정보
//                    if (
//                            (beforeItemDetail.getBasicDescription() == null && itemRequestDto.getBasicDescription() != null)
//                                    || (beforeItemDetail.getBasicDescription() != null && !beforeItemDetail.getBasicDescription().equals(itemRequestDto.getBasicDescription()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setBasicDesc(itemRequestDto.getBasicDescription());
//
//                    // 상품상세 주요 정보
//                    if (
//                            (beforeItemDetail.getDetaillDescription() == null && itemRequestDto.getDetaillDescription() != null)
//                                    || (beforeItemDetail.getDetaillDescription() != null && !beforeItemDetail.getDetaillDescription().equals(itemRequestDto.getDetaillDescription()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setDetlDesc(itemRequestDto.getDetaillDescription());
//
//                    // 기타정보
//                    if (
//                            (beforeItemDetail.getEtcDescription() == null && itemRequestDto.getEtcDescription() != null)
//                                    || (beforeItemDetail.getEtcDescription() != null && !beforeItemDetail.getEtcDescription().equals(itemRequestDto.getEtcDescription()))
//                    ) {
//                        differentCount++;
//                    }
//                    itemRegistApprVo.setEtcDesc(itemRequestDto.getEtcDescription());
//
//                    // 인증정보
//                    boolean certFlag = true; // 인증정보는 항상 저장 - 모두 삭제인 경우와 변경이 안된 경우 구분이 안되기 때문에. differentCount만 체크
//                    if(beforeItemCertList.size() != itemRequestDto.getAddItemCertificationList().size()) {
////  							certFlag = true;
//                        differentCount++;
//                    }else {
//                        for(ItemCertificationListVo beforeItemCertInfo : beforeItemCertList) {
//                            String beforeCertInfoId = beforeItemCertInfo.getIlCertificationId();
//
//                            boolean isSame = false;
//                            for(ItemCertificationDto afterItemCertInfo : itemRequestDto.getAddItemCertificationList()) {
//                                String afterCertInfoId = afterItemCertInfo.getIlCertificationId();
//
//                                if(beforeCertInfoId.equals(afterCertInfoId)) {
//                                    isSame = true;
//                                    break;
//                                }
//                            }
//
//                            if (isSame == false) {
////  	  								certFlag = true;
//                                differentCount++;
//                                break;
//                            }
//                        }
//                    }
//
//                    // 이미지 정보
//                    boolean imageFlag = false;
//                    if(beforeItemImageList.size() != itemRequestDto.getItemImageOrderList().size()) {
//                        imageFlag = true;
//                        differentCount++;
//                    }else {
//                        if (
//                                !itemRequestDto.getItemImageNameListToDelete().isEmpty() // 삭제할 파일 목록 없음
//                                        || !itemRequestDto.getItemImageUploadResultList().isEmpty() // 신규 업로드 파일 목록 없음
//                                        || itemRequestDto.isImageSortOrderChanged() // 정렬 순서 변경 없음
//                        )
//                        {
//                            imageFlag = true;
//                            differentCount++;
//                        }
//                    }
//
//                    // 영양 정보
//                    boolean nurFlag = true; // 영양정보는 항상 저장 - 모두 삭제인 경우와 변경이 안된 경우 구분이 안되기 때문에. differentCount만 체크
//                    if(beforeItemNurList.size() != itemRequestDto.getAddItemNutritionDetailList().size()) {
////  							nurFlag = true;
//                        differentCount++;
//                    }else {
//                        for(ItemNutritionDetailVo beforeItemNurInfo : beforeItemNurList) {
//                            String beforeNurCode = beforeItemNurInfo.getNutritionCode();
//                            Double beforeNurPercnt = beforeItemNurInfo.getNutritionPercent() == null ? new Double(0.0) : beforeItemNurInfo.getNutritionPercent();
//                            Double beforeNurQty	   = beforeItemNurInfo.getNutritionQuantity() == null ? new Double(0.0) : beforeItemNurInfo.getNutritionQuantity();
//
//                            boolean isSame = false;
//                            for(ItemNutritionDetailDto afterItemNurInfo : itemRequestDto.getAddItemNutritionDetailList()) {
//                                String afterNurCode = afterItemNurInfo.getNutritionCode();
//                                Double afterNurPercnt = afterItemNurInfo.getNutritionPercent() == null ? new Double(0.0) : afterItemNurInfo.getNutritionPercent();
//                                Double afterNurQty	   = afterItemNurInfo.getNutritionQuantity() == null ? new Double(0.0) : afterItemNurInfo.getNutritionQuantity();
//
//                                if(beforeNurCode.equals(afterNurCode) && beforeNurPercnt.compareTo(afterNurPercnt) == 0 && beforeNurQty.compareTo(afterNurQty) == 0 ) {
//                                    isSame = true;
//                                    break;
//                                }
//                            }
//
//                            if (isSame == false) {
////  	  								nurFlag = true;
//                                differentCount++;
//                                break;
//                            }
//                        }
//                    }
//
//                    // 고시정보
//                    boolean specFlag = true; // 고시정보는 항상 저장 - insert / delete로 처리하기 때문. differentCount만 체크
//                    if(beforeItemSpecList.size() != itemRequestDto.getAddItemSpecValueList().size()) {
////  							specFlag = true;
//                        differentCount++;
//                    }else {
//                        for(ItemSpecValueVo beforeItemSpecInfo : beforeItemSpecList) {
//                            String beforeSpecId = String.valueOf(beforeItemSpecInfo.getIlSpecFieldId());
//                            Boolean beforeDirectYn = beforeItemSpecInfo.getDirectYn() == null ? false : beforeItemSpecInfo.getDirectYn();
//                            String beforeSpecFieldValue = beforeItemSpecInfo.getSpecFieldValue();
//
//                            boolean isSame = false;
//                            for(ItemSpecValueRequestDto afterItemSpecInfo : itemRequestDto.getAddItemSpecValueList()) {
//                                String afterSpecId = String.valueOf(afterItemSpecInfo.getIlSpecFieldId());
//                                Boolean afterDirectYn = afterItemSpecInfo.getDirectYn() == null ? false : afterItemSpecInfo.getDirectYn();
//                                String afterSpecFieldValue = afterItemSpecInfo.getSpecFieldValue();
//
//                                if(beforeSpecId.equals(afterSpecId)) {
//                                    if (beforeDirectYn) {
//                                        if (afterDirectYn && beforeSpecFieldValue.equals(afterSpecFieldValue)) {
//                                            isSame = true;
//                                            break;
//                                        }
//                                    }
//                                    else {
//                                        isSame = true;
//                                        break;
//                                    }
//                                }
//                            }
//
//                            if (isSame == false) {
////  	  								specFlag = true;
//                                differentCount++;
//                                break;
//                            }
//                        }
//                    }
//
//
//                    if(differentCount == 0) {
//                        enums = GoodsEnums.GoodsApprProcStatus.NOT_DIFFERENT_ITEM;
//                    }
//                    else if(differentCount > 0){
//                        if(itemApprInfo == null || itemApprInfo.getApprStat().equals(ApprovalEnums.ApprovalStatus.DENIED.getCode())
//                                || itemApprInfo.getApprStat().equals(ApprovalEnums.ApprovalStatus.CANCEL.getCode())
//                                || itemApprInfo.getApprStat().equals(ApprovalEnums.ApprovalStatus.DISPOSAL.getCode())
//                                || itemApprInfo.getApprStat().equals(ApprovalEnums.ApprovalStatus.APPROVED_BY_SYSTEM.getCode())
//                                || itemApprInfo.getApprStat().equals(ApprovalEnums.ApprovalStatus.APPROVED.getCode())) {	//승인 내역이 없거나, 반려/요청철회/폐기/승인완료(시스템)/승인완료 상태일때
//                            goodsItemRegisterService.addItemAppr(itemRegistApprVo);
//                            String ilItemApprId = itemRegistApprVo.getIlItemApprId();
//
//                            System.out.println("#### ilItemApprId --->"+ itemRegistApprVo.getIlItemApprId());
//                            itemRegistApprVo.setPrevApprStat(ApprovalEnums.ApprovalStatus.NONE.getCode());
//                            itemRegistApprVo.setStatusCmnt(null);
//                            goodsItemRegisterService.addItemApprStatusHistory(itemRegistApprVo);
//
//                            if(certFlag) {
//                                for(ItemCertificationDto afterItemCertInfo : itemRequestDto.getAddItemCertificationList()) {
//                                    ItemCertificationApprVo itemCertificationApprVo = new ItemCertificationApprVo();
//                                    itemCertificationApprVo.setIlItemApprId(ilItemApprId);
//                                    itemCertificationApprVo.setIlItemCd(itemRequestDto.getIlItemCode());
//                                    itemCertificationApprVo.setIlCertificationId(afterItemCertInfo.getIlCertificationId());
//                                    itemCertificationApprVo.setCertificationDesc(afterItemCertInfo.getCertificationDescription());
//                                    itemCertificationApprVo.setCreateId(userId);
//
//                                    goodsItemRegisterService.addItemCertificationAppr(itemCertificationApprVo);
//                                }
//                            }
//
//                            if(imageFlag) {
//                                List<ItemImageRegisterVo> totalItemImageRegisterList = new ArrayList<>(); // 기존 데이터 삭제 후 신규 저장할 품목 이미지 Vo 목록
//
//                                // 신규 업로드 품목 이미지 존재시 : 해당 품목 이미지 등록 VO 와 해당 품목 이미지의 리사이징 파일 생성
//                                if (!itemRequestDto.getItemImageUploadResultList().isEmpty()) {
//                                    totalItemImageRegisterList = goodsItemModifyService.generateNewItemImageList(itemRequestDto);
//                                }
//
//                                // 화면에서 전송한 상품 이미지 정렬 순서 배열 존재시 : 삭제된 이미지 제외한 기존 등록된 이미지 Data 참조하여 품목 이미지 Vo 목록 생성
//                                if (!itemRequestDto.getItemImageOrderList().isEmpty()) {
//
//                                    totalItemImageRegisterList.addAll(goodsItemModifyService.generateReSortedItemImageList(itemRequestDto, beforeItemImageList));
//
//                                }
//
//                                /*
//                                 * 신규 품목 이미지 VO Insert
//                                 */
//                                for (ItemImageRegisterVo itemImageRegisterVo : totalItemImageRegisterList) {
//                                    itemImageRegisterVo.setIlItemApprId(ilItemApprId);
//                                    itemImageRegisterVo.setCreateId(Long.valueOf(userId));
//                                    goodsItemRegisterService.addItemImageAppr(itemImageRegisterVo);
//                                }
//                            }
//
//                            if(nurFlag) {
//                                int i = 0;
//                                for(ItemNutritionDetailDto afterItemNurInfo : itemRequestDto.getAddItemNutritionDetailList()) {
//                                    ItemNutritionApprVo itemNutritionApprVo = new ItemNutritionApprVo();
//                                    itemNutritionApprVo.setIlItemApprId(ilItemApprId);
//                                    itemNutritionApprVo.setIlItemCd(itemRequestDto.getIlItemCode());
//                                    itemNutritionApprVo.setErpNutritionPercent(afterItemNurInfo.getErpNutritionPercent());
//                                    itemNutritionApprVo.setErpNutritionQuantity(afterItemNurInfo.getErpNutritionQuantity());
//                                    itemNutritionApprVo.setNutritionCode(afterItemNurInfo.getNutritionCode());
//                                    itemNutritionApprVo.setNutritionPercent(afterItemNurInfo.getNutritionPercent());
//                                    itemNutritionApprVo.setNutritionQuantity(afterItemNurInfo.getNutritionQuantity());
//                                    itemNutritionApprVo.setSort(i);
//                                    i++;
//                                    itemNutritionApprVo.setCreateId(userId);
//
//                                    goodsItemRegisterService.addItemNutritionAppr(itemNutritionApprVo);
//                                }
//                            }
//
//                            if(specFlag) {
//                                for(ItemSpecValueRequestDto afterItemSpecInfo : itemRequestDto.getAddItemSpecValueList()) {
//
//
//                                    ItemSpecApprVo itemSpecApprVo = new ItemSpecApprVo();
//                                    itemSpecApprVo.setIlItemApprId(ilItemApprId);
//                                    itemSpecApprVo.setIlItemCode(itemRequestDto.getIlItemCode());
//                                    itemSpecApprVo.setIlSpecFieldId(afterItemSpecInfo.getIlSpecFieldId());
//                                    itemSpecApprVo.setDirectYn(afterItemSpecInfo.getDirectYn());
//                                    itemSpecApprVo.setSpecFieldValue(afterItemSpecInfo.getSpecFieldValue());
//                                    itemSpecApprVo.setCreateId(Long.valueOf(userId));
//
//                                    goodsItemRegisterService.addItemSpecAppr(itemSpecApprVo);
//                                }
//                            }
//
//                            if(beforeItemDetail.getItemStatusTp().equals("ITEM_STATUS_TP.REGISTER")) {
//                                goodsRegistBiz.updateGoodsSaleStatusToWaitByItemAppr(itemRequestDto.getIlItemCode());
//                            }
//
//                            enums = GoodsEnums.GoodsApprProcStatus.CLINET_APPR_REQUEST;
//                        }
//                        else {
//                            enums = GoodsEnums.GoodsApprProcStatus.APPR_DUPLICATE;
//                        }
//                    }
//                }
//            }
//            else {
//                enums = GoodsEnums.GoodsApprProcStatus.NONE_GOODS_ID;
//            }
//        }
//
//
//        return ApiResult.result(enums);
//    }
//}
