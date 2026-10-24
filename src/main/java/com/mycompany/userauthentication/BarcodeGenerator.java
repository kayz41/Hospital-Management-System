package com.mycompany.userauthentication;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import java.awt.image.BufferedImage;

public class BarcodeGenerator {

    public static BufferedImage generateBarcode(String text)
            throws WriterException {

        Code128Writer writer = new Code128Writer();

        BitMatrix bitMatrix = writer.encode(
                text,
                BarcodeFormat.CODE_128,
                300,
                100
        );

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}