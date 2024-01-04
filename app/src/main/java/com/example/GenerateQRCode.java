package com.example;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;



import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class GenerateQRCode {

	public static void main() throws WriterException, IOException {

		System.out.println("DONE");
	}

	public  Bitmap createQRImage(File qrFile, String qrCodeText, int size, String fileType,String type)

			throws WriterException, IOException {
		// Create the ByteMatrix for the QR-Code that encodes the given String
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		 qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix;
		if(type.equals("QR")) {
			byteMatrix= qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);

			Bitmap ImageBitmap = Bitmap.createBitmap(150, 150, Bitmap.Config.ALPHA_8.ARGB_8888);

			for (int i = 0; i < 150; i++) {//width
				for (int j = 0; j < 150; j++) {//height
					ImageBitmap.setPixel(i, j, byteMatrix.get(i, j) ? Color.BLACK: Color.WHITE);
				}
			}

			if (ImageBitmap != null) {

			} else {

			}

			return  ImageBitmap;
		}
		else
		{
			MultiFormatWriter writer = new MultiFormatWriter();
			String finalData = Uri.encode(qrCodeText);

			// Use 1 as the height of the matrix as this is a 1D Barcode.
			BitMatrix bm = writer.encode(finalData, BarcodeFormat.CODE_128, 150, 1);
			int bmWidth = bm.getWidth();

			Bitmap imageBitmap = Bitmap.createBitmap(150, 100, Bitmap.Config.ARGB_8888);

			for (int i = 0; i < 150; i++) {
				// Paint columns of width 1
				int[] column = new int[150];
				Arrays.fill(column, bm.get(i, 0) ? Color.BLACK : Color.WHITE);
				imageBitmap.setPixels(column, 0, 1, i, 0, 1, 100);
			}

			return imageBitmap;
		}


	}


	public  Bitmap createBarcodePrintImage(File qrFile, String qrCodeText, int size, String fileType,String type)

			throws WriterException, IOException {
		// Create the ByteMatrix for the QR-Code that encodes the given String
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix;

			MultiFormatWriter writer = new MultiFormatWriter();
			String finalData = Uri.encode(qrCodeText);

			// Use 1 as the height of the matrix as this is a 1D Barcode.
			BitMatrix bm = writer.encode(finalData, BarcodeFormat.CODE_128, 350, 1);
			int bmWidth = bm.getWidth();

			Bitmap imageBitmap = Bitmap.createBitmap(350, 100, Bitmap.Config.ARGB_8888);

			for (int i = 0; i < 350; i++) {
				// Paint columns of width 1
				int[] column = new int[350];
				Arrays.fill(column, bm.get(i, 0) ? Color.BLACK : Color.WHITE);
				imageBitmap.setPixels(column, 0, 1, i, 0, 1, 100);
			}

			return imageBitmap;
		}



}
