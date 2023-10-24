package com.example.topdf.services;

import java.io.ByteArrayInputStream;

public interface PdfService {
    ByteArrayInputStream convertHtmlToPdf(String htmlContent);
}