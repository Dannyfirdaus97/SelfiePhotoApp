package com.mobilemerit.java;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;

public class ImageEffects {

	public static Bitmap doGreyscale(Bitmap src) {
		// konstanta faktor
		final double GS_RED = 0.299;
		final double GS_GREEN = 0.587;
		final double GS_BLUE = 0.114;

		// buat keluaran bitmap
		Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
				src.getConfig());
		// informasi piksel
		int A, R, G, B;
		int pixel;

		// mendapat ukuran gambar
		int width = src.getWidth();
		int height = src.getHeight();

		// scan melalui setiap pixel
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// dapatkan satu warna pixel
				pixel = src.getPixel(x, y);
				// ambil warna semua saluran
				A = Color.alpha(pixel);
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);
				// konversi menjadi satu nilai tunggal
				R = G = B = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);
				// atur warna piksel baru ke keluaran bitmap
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;
	}

	public static Bitmap createSepiaToningEffect(Bitmap src, int depth,
			double red, double green, double blue) {
		// ukuran gambar
		int width = src.getWidth();
		int height = src.getHeight();
		// mendapat keluaran bitmap
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
		// grayscale konstanta
		final double GS_RED = 0.3;
		final double GS_GREEN = 0.59;
		final double GS_BLUE = 0.11;
		// informasi warna
		int A, R, G, B;
		int pixel;

		// scan melalui semua piksel
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// mendapatkan warna pixel
				pixel = src.getPixel(x, y);
				// mendapatkan warna pada setiap saluran
				A = Color.alpha(pixel);
				R = Color.red(pixel);
				G = Color.green(pixel);
				B = Color.blue(pixel);
				// oleskan sampel grayscale
				B = G = R = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);

				// gunakan tingkat intensitas untuk mengencangkan setiap saluran
				R += (depth * red);
				if (R > 255) {
					R = 255;
				}

				G += (depth * green);
				if (G > 255) {
					G = 255;
				}

				B += (depth * blue);
				if (B > 255) {
					B = 255;
				}

				// atur warna pixel baru ke gambar output
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;
	}

	public static Bitmap doColorFilter(Bitmap src, double red, double green,
			double blue) {
		// ukuran gambar
		int width = src.getWidth();
		int height = src.getHeight();
		// buat bitmap output
		Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
		// color information
		int A, R, G, B;
		int pixel;

		// scan melalui semua piksel
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				// mendapatkan warna pixel
				pixel = src.getPixel(x, y);
				// menerapkan penyaringan pada setiap saluran R, G, B
				A = Color.alpha(pixel);
				R = (int) (Color.red(pixel) * red);
				G = (int) (Color.green(pixel) * green);
				B = (int) (Color.blue(pixel) * blue);
				// atur pixel warna baru ke bitmap output
				bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			}
		}

		// return final image
		return bmOut;
	}
	public static Bitmap doGamma(Bitmap src, double red, double green, double blue) {
	    // buat gambar output
	    Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
	    // mendapatkan ukuran gambar
	    int width = src.getWidth();
	    int height = src.getHeight();
	    // warna
	    int A, R, G, B;
	    int pixel;
	    // kurva nilai konstan
	    final int    MAX_SIZE = 256;
	    final double MAX_VALUE_DBL = 255.0;
	    final int    MAX_VALUE_INT = 255;
	    final double REVERSE = 1.0;
	 
	    // array gamma
	    int[] gammaR = new int[MAX_SIZE];
	    int[] gammaG = new int[MAX_SIZE];
	    int[] gammaB = new int[MAX_SIZE];
	 
	    // menetapkan nilai untuk setiap saluran gamma
	    for(int i = 0; i < MAX_SIZE; ++i) {
	        gammaR[i] = (int)Math.min(MAX_VALUE_INT,
	                (int)((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / red)) + 0.5));
	        gammaG[i] = (int)Math.min(MAX_VALUE_INT,
	                (int)((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / green)) + 0.5));
	        gammaB[i] = (int)Math.min(MAX_VALUE_INT,
	                (int)((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / blue)) + 0.5));
	    }
	 
	    // aplikasikan tabel gamma
	    for(int x = 0; x < width; ++x) {
	        for(int y = 0; y < height; ++y) {
	            // mendapatkan warna pixel
	            pixel = src.getPixel(x, y);
	            A = Color.alpha(pixel);
	            // look up gamma
	            R = gammaR[Color.red(pixel)];
	            G = gammaG[Color.green(pixel)];
	            B = gammaB[Color.blue(pixel)];
	            // atur warna baru ke bitmap output
	            bmOut.setPixel(x, y, Color.argb(A, R, G, B));
	        }
	    }
	 
	    // return final image
	    return bmOut;
	}
	public static Bitmap applySnowEffect(Bitmap source) {
	    // mendapat ukuran gambar
	    int width = source.getWidth();
	    int height = source.getHeight();
	    int[] pixels = new int[width * height];
	    // dapatkan array piksel dari sumber
	    source.getPixels(pixels, 0, width, 0, 0, width, height);
	    // objek acak
	    Random random = new Random();
	     
	    int R, G, B, index = 0, thresHold = 50;
	    // iterasi melalui piksel
	    for(int y = 0; y < height; ++y) {
	        for(int x = 0; x < width; ++x) {
	            // dapatkan indeks arus dalam matriks 2D
	            index = y * width + x;              
	            // mendapat warna
	            R = Color.red(pixels[index]);
	            G = Color.green(pixels[index]);
	            B = Color.blue(pixels[index]);
	            // menghasilkan ambang batas
	            thresHold = random.nextInt(255);
	            if(R > thresHold && G > thresHold && B > thresHold) {
	                pixels[index] = Color.rgb(255, 255, 255);
	            }                           
	        }
	    }
	    // output bitmap                
	    Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	    bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
	    return bmOut;
	}
	
	public static Bitmap rotate(Bitmap src) {
	    // buat matriks baru
	    Matrix matrix = new Matrix();
	    // tingkat rotasi
	    matrix.postRotate(270);	 
	    // bitmap baru diputar menggunakan matriks
	    return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
	}
}
