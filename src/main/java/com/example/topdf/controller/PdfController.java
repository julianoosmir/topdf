package com.example.topdf.controller;

import com.example.topdf.model.DataMapper;
import com.example.topdf.model.Employee;
import com.example.topdf.services.PdfService;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/generatePdfFile")
    public void generatePdfFile(HttpServletResponse response, String contentToGenerate) throws IOException {
        ByteArrayInputStream byteArrayInputStream = pdfService.convertHtmlToPdf(contentToGenerate);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=file.pdf");
        IOUtils.copy(byteArrayInputStream, response.getOutputStream());
    }
    @PostMapping(value = "/generateDocument")
    public void generateDocument(HttpServletResponse response) throws IOException {
        List<Employee> employeeList = Arrays.asList(new Employee("teste","testando","teste@test"));

        String finalHtml = null;

        Context dataContext = dataMapper.setData(employeeList);

        finalHtml = springTemplateEngine.process("template", dataContext);

        ByteArrayInputStream byteArrayInputStream = pdfService.convertHtmlToPdf(finalHtml);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=file.pdf");
        IOUtils.copy(byteArrayInputStream, response.getOutputStream());
    }
}