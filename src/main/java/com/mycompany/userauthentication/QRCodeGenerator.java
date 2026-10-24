package com.mycompany.userauthentication;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;

public class QRCodeGenerator {

    public static BufferedImage generateQRCode(String text)
            throws Exception {

        QRCodeWriter writer = new QRCodeWriter();

        BitMatrix bitMatrix = writer.encode(
                text,
                BarcodeFormat.QR_CODE,
                200,
                200
        );

        return MatrixToImageWriter.toBufferedImage(bitMatrix);

    }

}