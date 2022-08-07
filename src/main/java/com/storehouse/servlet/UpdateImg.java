package com.storehouse.servlet;

import com.storehouse.utils.TencentCosUtil;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

/**
 * Vditor编辑器上传图片接口
 *
 * @author JanYork
 */
@WebServlet(name = "UpdateImg", value = "/UpdateImg")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 1024 * 1024)
public class UpdateImg extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //创建输出对象
        Writer out = response.getWriter();
        JSONObject json = new JSONObject();
        //获取前端传输的文件
        Part part = request.getPart("file[]");
        //获取文件名
        String fileName = part.getSubmittedFileName();
        //获取文件内容
        InputStream inputStream = part.getInputStream();
        //转换为字节数组
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String imgUrl = TencentCosUtil.uploadByteForCloud(bytes, fileName);
        json.put("msg", "上传成功");
        json.put("code", 0);
        json.put("name", fileName);
        json.put("url", imgUrl);
        out.write(json.toString());


    }
}
