package com.example.bookshop01.common.base;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.bookshop01.goods.vo.ImageFileVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class BaseController {
    private static final String CURR_IMAGE_REPO_PATH = "C:\\shopping\\file_repo";

    protected List<ImageFileVO> upload(MultipartHttpServletRequest multipartRequest) throws Exception {
        List<ImageFileVO> fileList = new ArrayList<>();
        Iterator<String> fileNames = multipartRequest.getFileNames();

        while (fileNames.hasNext()) {
            ImageFileVO imageFileVO = new ImageFileVO();

            String fileName = fileNames.next();
            imageFileVO.setFileType(fileName);

            MultipartFile mFile = multipartRequest.getFile(fileName);
            String originalFileName = mFile.getOriginalFilename();

            imageFileVO.setFileName(originalFileName);
            fileList.add(imageFileVO);

            if (mFile.getSize() != 0) {
                File tempDir = new File(CURR_IMAGE_REPO_PATH + "\\temp");
                if (!tempDir.exists()) {
                    tempDir.mkdirs();
                }

                mFile.transferTo(new File(tempDir, originalFileName));
            }
        }

        return fileList;
    }
    
	private void deleteFile(String fileName) {
		File file =new File(CURR_IMAGE_REPO_PATH+"\\"+fileName);
		try{
			file.delete();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

    @RequestMapping(value = "/*.do", method = { RequestMethod.POST, RequestMethod.GET })
    protected ModelAndView viewForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = (String) request.getAttribute("viewName");
        return new ModelAndView(viewName);
    }

    protected String calcSearchPeriod(String fixedSearchPeriod) {
        String beginDate;
        String endDate;

        DecimalFormat df = new DecimalFormat("00");
        Calendar cal = Calendar.getInstance();

        String endYear = Integer.toString(cal.get(Calendar.YEAR));
        String endMonth = df.format(cal.get(Calendar.MONTH) + 1);
        String endDay = df.format(cal.get(Calendar.DATE));
        endDate = endYear + "-" + endMonth + "-" + endDay;

        if (fixedSearchPeriod == null) {
            cal.add(Calendar.MONTH, -4);
        } else if (fixedSearchPeriod.equals("one_week")) {
            cal.add(Calendar.DAY_OF_YEAR, -7);
        } else if (fixedSearchPeriod.equals("two_week")) {
            cal.add(Calendar.DAY_OF_YEAR, -14);
        } else if (fixedSearchPeriod.equals("one_month")) {
            cal.add(Calendar.MONTH, -1);
        } else if (fixedSearchPeriod.equals("two_month")) {
            cal.add(Calendar.MONTH, -2);
        } else if (fixedSearchPeriod.equals("three_month")) {
            cal.add(Calendar.MONTH, -3);
        } else if (fixedSearchPeriod.equals("four_month")) {
            cal.add(Calendar.MONTH, -4);
        }

        String beginYear = Integer.toString(cal.get(Calendar.YEAR));
        String beginMonth = df.format(cal.get(Calendar.MONTH) + 1);
        String beginDay = df.format(cal.get(Calendar.DATE));
        beginDate = beginYear + "-" + beginMonth + "-" + beginDay;

        return beginDate + "," + endDate;
    }
}