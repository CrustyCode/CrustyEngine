package org.crusty.engine;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.PixelGrabber;
import java.awt.image.RGBImageFilter;

public class ImageManager {
	
	// This method returns true if the specified image has transparent pixels
	public static boolean hasAlpha(Image image) {
		// If buffered image, the color model is readily available
		if (image instanceof BufferedImage) {
			BufferedImage bimage = (BufferedImage)image;
			return bimage.getColorModel().hasAlpha();
		}

		// Use a pixel grabber to retrieve the image's color model;
		// grabbing a single pixel is usually sufficient
		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
		}

		// Get the image's color model
		ColorModel cm = pg.getColorModel();
		return cm.hasAlpha();
	}

//	public static BufferedImage toBufferedImage(Image image) { 
//		if (image instanceof BufferedImage) { 
//			return (BufferedImage)image; 
//		} 
//		// This code ensures that all the pixels in the image are loaded 
//		image = new ImageIcon(image).getImage(); // Determine if the image has transparent pixels; for this method's 
//		// implementation, see Determining If an Image Has Transparent Pixels  
//		boolean hasAlpha = hasAlpha(image); // Create a buffered image with a format that's compatible with the screen 
//		BufferedImage bimage = null; 
//		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
//		try { // Determine the type of transparency of the new buffered 
//			int transparency = Transparency.OPAQUE; 
//			if (hasAlpha) { 
//				transparency = Transparency.BITMASK; 
//			} // Create the buffered image 
//			GraphicsDevice gs = ge.getDefaultScreenDevice(); 
//			GraphicsConfiguration gc = gs.getDefaultConfiguration(); 
//			bimage = gc.createCompatibleImage( image.getWidth(null), image.getHeight(null), transparency); 
//		} catch (HeadlessException e) { // The system does not have a screen 
//
//			
////			Graphics2D g2 = bi.createGraphics();
////			image = g2.getDeviceConfiguration().createCompatibleImage(
////					bi.getWidth(), 
////					bi.getHeight(), 
////					Transparency.TRANSLUCENT);
//			
//		} if (bimage == null) { 
//			// Create a buffered image using the default color model 
//			int type = BufferedImage.TYPE_INT_RGB; 
//			if (hasAlpha) { type = BufferedImage.TYPE_INT_ARGB; } 
//			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type); 
//		} // Copy image to buffered image 
//		Graphics g = bimage.createGraphics(); // Paint the image onto the buffered image 
//		g.drawImage(image, 0, 0, null); 
//		g.dispose(); 
//		return bimage;
//	}
	
    private static BufferedImage toBufferedImage(Image image) {
    	BufferedImage bufferedImage = new BufferedImage(	image.getWidth(null), 
    														image.getHeight(null), 
    														BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2 = bufferedImage.createGraphics();
    	g2.drawImage(image, 0, 0, null);
    	g2.dispose();

    	return bufferedImage;
    }
    
//    private BufferedImage toBufferedImage(Image image)
//    {
//    	BufferedImage dest = new BufferedImage(
//          image.getWidth(arg0)width, height, BufferedImage.TYPE_INT_ARGB);
//    	Graphics2D g2 = dest.createGraphics();
//    	g2.drawImage(image, 0, 0, null);
//    	g2.dispose();
//    	return dest;
//    }

//	public static BufferedImage makeColorTransparent (BufferedImage im, final Color color) {
//		ImageFilter filter = new RGBImageFilter() {
//			// the color we are looking for... Alpha bits are set to opaque
//			public int markerRGB = color.getRGB() | 0xFF000000;
//
//			public final int filterRGB(int x, int y, int rgb) {
//				if ( ( rgb | 0xFF000000 ) == markerRGB ) {
//					// Mark the alpha bits as zero - transparent
//					return 0x00FFFFFF & rgb;
//				}
//				else {
//					// nothing to do
//					return rgb;
//				}
//			}
//		};
//		ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
//		return toBufferedImage(Toolkit.getDefaultToolkit().createImage(ip));
//	}

    public static BufferedImage makeColorTransparent(BufferedImage image, Color c1) // New one
    {
    	// Primitive test, just an example
    	final int r1 = c1.getRed();
    	final int g1 = c1.getGreen();
    	final int b1 = c1.getBlue();
    	final int r2 = r1; //c2.getRed();
    	final int g2 = g1; //c2.getGreen();
    	final int b2 = b1; //c2.getBlue();
    	ImageFilter filter = new RGBImageFilter()
    	{
    		public final int filterRGB(int x, int y, int rgb)
    		{
    			int r = (rgb & 0xFF0000) >> 16;
    			int g = (rgb & 0xFF00) >> 8;
    			int b = rgb & 0xFF;
    			if (r >= r1 && r <= r2 &&
    					g >= g1 && g <= g2 &&
    					b >= b1 && b <= b2)
    			{
    				// Set fully transparent but keep color
    				return rgb & 0xFFFFFF;
    			} else {
    				//        	  System.out.println("Not setting transparent: " + rgb + " x: " + x + " y: " + y);
    			}
    			return rgb;
    		}
    	};
    	ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
    	return toBufferedImage(Toolkit.getDefaultToolkit().createImage(ip));
    }

//    public static BufferedImage makeColorTransparent(BufferedImage im, final Color color) {
//    	ImageFilter filter = new RGBImageFilter() {
//
//    		// the color we are looking for... Alpha bits are set to opaque
//    		public int markerRGB = color.getRGB() | 0xFF000000;
//
//    		public final int filterRGB(int x, int y, int rgb) {
//    			if ((rgb | 0xFF000000) == markerRGB) {
//    				// Mark the alpha bits as zero - transparent
//    				return 0x00FFFFFF & rgb;
//    			} else {
//    				// nothing to do
//    				return rgb;
//    			}
//    		}
//    	};
//
//    	ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
//    	return toBufferedImage(Toolkit.getDefaultToolkit().createImage(ip));
//    }

	public static BufferedImage flipImage(BufferedImage image) {
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-image.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(image, null);
	}
}
