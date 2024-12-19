import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("ironman.ppm");
		print(tinypic);
		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image = tinypic;
		Color[][] imagemorph = scaled(tinypic, 100, 150);

		morph(image ,imagemorph,3);
		display(imagemorph);
		// Tests the horizontal flipping of an image:
		//image = flippedHorizontally(tinypic);
		//System.out.println();
		//print(image);
		
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
		In in = new In(fileName);
		// Reads the file header, ignoring the first and the third lines.
		in.readString();
		int numCols = in.readInt();
		int numRows = in.readInt();
		in.readInt();
		// Creates the image array
		Color[][] image = new Color[numRows][numCols];
		// Reads the RGB values from the file into the image array. 
		// For each pixel (i,j), reads 3 values from the file,
		// creates from the 3 colors a new Color object, and 
		// makes pixel (i,j) refer to that object.
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				int r = in.readInt();
				int g = in.readInt();
				int b = in.readInt();
				image[i][j] = new Color(r, g, b);
			}
		}
		
		return image;
	}

    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	private static void print(Color[][] image) {
		int full = image.length;
		int row = image[0].length;
		int i = 0;
		while (i < full) {
			for (int j = 0; j < row;) {
				Color c = image[i][j];
				print(c);
				if(j == (row-1) && i == (full-1)){
					i = full;
					j = row;
					System.out.println();	
				}else if(j == (row -1) && i < (full-1)){
						j = 0;
						i++;
						System.out.println();
							}else{
								j++;
							}
			}
		}
	}
	
	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
		int numRows = image.length;
		int numCols = image[0].length;
		Color[][] imageFlipHoriz = new Color[numRows][numCols];
		for (int i = 0; i < numRows; i++) {  
			for (int j = 0; j < numCols; j++) {   
				imageFlipHoriz[i][j] = image[i][numCols - 1 - j];
			}
		}
		return imageFlipHoriz;
	}
	
	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image){
		int numRows = image.length;
		int numCols = image[0].length;
		Color[][] imageFlipVertic = new Color[numRows][numCols];
		for (int j = 0; j < numCols; j++) {  
			for (int i = 0; i < numRows; i++) {   
				imageFlipVertic[i][j] = image[numRows- i -1][j];
			}
		}
		return imageFlipVertic;
	}
	
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Integer DoubleToInt(double X){
		String strX = String.valueOf(X);
		strX = strX.substring(0, strX.length()-2);
		int intX = Integer.parseInt(strX);
		return intX;
	}
	private static Color luminance(Color pixel) {
		int lum = (int) (0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
		Color pixelLum = new Color(lum, lum, lum);
			return pixelLum;
		}
		
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
		int numRows =image.length;
		int numCols =image[0].length;
		Color [][] grayImage = new Color[numRows][numCols];
		for (int i = 0; i < numRows; i++) {  
			for (int j = 0; j < numCols; j++) {   
				grayImage[i][j]=luminance(image[i][j]);
			}
		}
		return grayImage;
	}
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
		int H0 = image.length;
		int W0 = image[0].length;
		Color[][] imageScaled = new Color[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int row = (int)(i * ((double) H0 / height));
				int col = (int)(j * ((double) W0 / width));
				imageScaled[i][j]=image[row][col];
		}
	}
		return imageScaled;
	}
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
		if(c1 == null || c2 == null){
			System.out.println("is null");
		}
		int redNew = (int) (alpha * c1.getRed() + (1-alpha) * c2.getRed());
		int greenNew = (int) (alpha * c1.getGreen() + (1-alpha) * c2.getGreen());
		int blueNew = (int) (alpha * c1.getBlue() + (1-alpha) * c2.getBlue());
		Color blendedPix = new Color(redNew, greenNew, blueNew);

	return blendedPix;
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
		int numRows =image1.length;
		int numCols =image2[0].length;
		Color[][] imageBlend = new Color[numRows][numCols];
		for (int i = 0; i < numRows; i++) {  
			for (int j = 0; j < numCols; j++) {   
				imageBlend[i][j] = blend(image1[i][j], image2[i][j], alpha);
			}
		}
		return imageBlend;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
		Color [][] toTarget = null;
		if(source.length != target.length || source[0].length != target[0].length){
			toTarget = scaled(target, source[0].length, source.length);
		} else{
			toTarget = target;
		}
		for(int i = 0; i < n; i++){
			display(blend(source, toTarget, (n-1)/n));
			StdDraw.pause(500);
		}
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

