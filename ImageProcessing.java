import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
@SuppressWarnings("ALL")
public class ImageProcessing {
	public static void main(String[] args) {
    // The provided images are apple.jpg, flower.jpg, and kitten.jpg
		int[][] imageData = imgToTwoD("C:\\Users\\Cameron\\Desktop\\dev\\codecademy\\ImageProcessing\\out\\production\\ImageProcessing\\assets\\apple.jpg");
    // Or load your own image using a URL!
		//int[][] imageData = imgToTwoD("https://content.codecademy.com/projects/project_thumbnails/phaser/bug-dodger.png");
		//System.out.println(Arrays.deepToString(imageData));
		assert imageData != null;
		int[][] trimmed = trimBorders(imageData, 1500);
        int[][] blankCanvas = new int[500][500];
        int[][] randomPainting = paintRandomImage(blankCanvas);
        int[][] rectangle = paintRectangle(blankCanvas, 100, 200, 50, 50, 178);
        int[][] paintRectangle = generateRectangles(blankCanvas, 1000);
		System.out.println("yo");
		twoDToImage(trimmed, "C:\\Users\\Cameron\\Desktop\\dev\\codecademy\\ImageProcessing\\assets\\trimmed_apple.jpg");
        twoDToImage(negativeColor(imageData), "C:\\Users\\Cameron\\Desktop\\dev\\codecademy\\ImageProcessing\\assets\\negative_apple.jpg");
        twoDToImage(stretchHorizontally(imageData), "C:\\Users\\Cameron\\Desktop\\dev\\codecademy\\ImageProcessing\\assets\\horizontalStretch_apple.jpg");
        twoDToImage(shrinkVertically(imageData), "C:\\Users\\Cameron\\Desktop\\dev\\codecademy\\ImageProcessing\\assets\\shrinkVertically_apple.jpg");
        twoDToImage(invertImage(imageData), "C:\\Users\\Cameron\\Desktop\\dev\\codecademy\\ImageProcessing\\assets\\invertImage_apple.jpg");
        twoDToImage(colorFilter(imageData, 100, 100, 100), "C:\\Users\\Cameron\\Desktop\\dev\\codecademy\\ImageProcessing\\out\\production\\ImageProcessing\\assets\\colorFilter_apple.jpg");
        twoDToImage(randomPainting, "C:\\\\Users\\\\Cameron\\\\Desktop\\\\dev\\\\codecademy\\\\ImageProcessing\\\\assets\\\\paintRandomImage.jpg");
        twoDToImage(rectangle, "C:\\\\Users\\\\Cameron\\\\Desktop\\\\dev\\\\codecademy\\\\ImageProcessing\\\\assets\\\\rectangle.jpg");
        twoDToImage(paintRectangle, "C:\\\\Users\\\\Cameron\\\\Desktop\\\\dev\\\\codecademy\\\\ImageProcessing\\\\assets\\\\paintRectangle.jpg");
        
		// int[][] allFilters = stretchHorizontally(shrinkVertically(colorFilter(negativeColor(trimBorders(invertImage(imageData), 50)), 200, 20, 40)));
		// Painting with pixels
	}
	// Image Processing Methods
	public static int[][] trimBorders(int[][] imageTwoD, int pixelCount) {
		// Example Method
		if (imageTwoD.length > pixelCount * 2 && imageTwoD[0].length > pixelCount * 2) {
			int[][] trimmedImg = new int[imageTwoD.length - pixelCount * 2][imageTwoD[0].length - pixelCount * 2];
			for (int i = 0; i < trimmedImg.length; i++) {
				for (int j = 0; j < trimmedImg[i].length; j++) {
					trimmedImg[i][j] = imageTwoD[i + pixelCount][j + pixelCount];
				}
			}
			return trimmedImg;
		} else {
			System.out.println("Cannot trim that many pixels from the given image.");
			return imageTwoD;
		}
	}
	public static int[][] negativeColor(int[][] imageTwoD) {
		int[][] negative = new int[imageTwoD.length][imageTwoD[0].length];
    for(int i = 0; i < imageTwoD.length; i++){
      for(int j = 0; j < imageTwoD[i].length; j++){
        int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
        rgba[0] = 255 - rgba[0];
        rgba[1] = 255 - rgba[1];
        rgba[2] = 255 - rgba[2];
        negative[i][j] = getColorIntValFromRGBA(rgba);
      }
    }
		return negative;
	}
	public static int[][] stretchHorizontally(int[][] imageTwoD) {
		int[][] horizontal = new int[imageTwoD.length][imageTwoD[0].length*2];
        for(int i = 0; i < imageTwoD.length; i++){
            for(int j = 0; j < imageTwoD[i].length; j++){
                int it = j * 2;
                horizontal[i][it] = imageTwoD[i][j];
                horizontal[i][it + 1] = imageTwoD[i][j];
            }
        }
		return horizontal;
	}
	public static int[][] shrinkVertically(int[][] imageTwoD) {
		int[][] vertical = new int[imageTwoD.length / 2][imageTwoD[0].length];
        for(int i = 0; i < imageTwoD[0].length; i++){
            for(int j = 0; j < imageTwoD.length - 1; j+=2){
                vertical[j/2][i] = imageTwoD[j][i];
            }
        }
		return vertical;
	}
	public static int[][] invertImage(int[][] imageTwoD) {
		int[][] invert = new int[imageTwoD.length][imageTwoD[0].length];
        for(int i = 0; i < imageTwoD.length; i++){
            for(int j = 0; j < imageTwoD[i].length; j++){
                invert[i][j] = imageTwoD[(imageTwoD.length - 1) - i][(imageTwoD[i].length - 1) - j];
            }
        }
		return invert;
	}
	public static int[][] colorFilter(int[][] imageTwoD, int redChangeValue, int greenChangeValue, int blueChangeValue) {
		int[][] filter = new int[imageTwoD.length][imageTwoD[0].length];
    for(int i = 0; i < imageTwoD.length; i++){
      for(int j = 0; j < imageTwoD[i].length; j++){
        int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
        rgba[0] = rgba[0] + redChangeValue;
        rgba[1] = rgba[1] + greenChangeValue;
        rgba[2] = rgba[2] + blueChangeValue;
        if(rgba[0] > 255){
          rgba[0] = 255;
        }
        if(rgba[1] > 255){
          rgba[1] = 255;
        }
        if(rgba[2] > 255){
          rgba[2] = 255;
        }
        filter[i][j] = getColorIntValFromRGBA(rgba);
      }
    }
		return filter;
	}
	// Painting Methods
	public static int[][] paintRandomImage(int[][] canvas) {
		Random rand = new Random();
        for(int i = 0; i < canvas.length; i++){
            for(int j = 0; j < canvas.length; j++){
               int a = rand.nextInt(256);
               int b = rand.nextInt(256);
               int c = rand.nextInt(256);
               
               int[] array = {a, b, c, 255};

               canvas[i][j] = getColorIntValFromRGBA(array);
            }
        }
		return canvas;
	}
	public static int[][] paintRectangle(int[][] canvas, int width, int height, int rowPosition, int colPosition, int color) {
		for(int i = 0; i < canvas.length; i++){
            for(int j = 0; j < canvas.length; j++){
                if(i >= rowPosition && i <= rowPosition + width && j >= colPosition && j <= colPosition + height){
                   canvas[i][j] =  color;
                }
            }
        }
		return canvas;
	}
	public static int[][] generateRectangles(int[][] canvas, int numRectangles) {
		Random rand = new Random();
        for(int i = 0; i < canvas.length; i++){
            for(int j = 0; j < canvas.length; j++){
                int width = rand.nextInt(canvas[0].length);
                int height = rand.nextInt(canvas.length);
                int rowPosition = rand.nextInt(canvas.length - height);
                int colPosition = rand.nextInt(canvas[0].length - width);
                int a = rand.nextInt(256);
                int b = rand.nextInt(256);
                int c = rand.nextInt(256);
               
                int[] array = {a, b, c, 255};

                int color = getColorIntValFromRGBA(array);

                paintRectangle(canvas, width, height, rowPosition, colPosition, color);
            }

        }
		return canvas;
	}
	// Utility Methods
	public static int[][] imgToTwoD(String inputFileOrLink) {
		try {
			BufferedImage image = null;
			if (inputFileOrLink.substring(0, 4).toLowerCase().equals("http")) {
				URL imageUrl = new URL(inputFileOrLink);
				image = ImageIO.read(imageUrl);
				if (image == null) {
					System.out.println("Failed to get image from provided URL.");
				}
			} else {
				image = ImageIO.read(new File(inputFileOrLink));
			}
			int imgRows = image.getHeight();
			int imgCols = image.getWidth();
			int[][] pixelData = new int[imgRows][imgCols];
			for (int i = 0; i < imgRows; i++) {
				for (int j = 0; j < imgCols; j++) {
					pixelData[i][j] = image.getRGB(j, i);
				}
			}
			return pixelData;
		} catch (Exception e) {
			System.out.println("Failed to load image: " + e.getLocalizedMessage());
			return null;
		}
	}
	public static void twoDToImage(int[][] imgData, String fileName) {
		try {
			int imgRows = imgData.length;
			int imgCols = imgData[0].length;
			BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < imgRows; i++) {
				for (int j = 0; j < imgCols; j++) {
					result.setRGB(j, i, imgData[i][j]);
				}
			}
			File output = new File(fileName);
			ImageIO.write(result, "jpg", output);
		} catch (Exception e) {
			System.out.println("Failed to save image: " + e.getLocalizedMessage());
		}
	}
	public static int[] getRGBAFromPixel(int pixelColorValue) {
		Color pixelColor = new Color(pixelColorValue);
		return new int[] { pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelColor.getAlpha() };
	}
	public static int getColorIntValFromRGBA(int[] colorData) {
		if (colorData.length == 4) {
			Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
			return color.getRGB();
		} else {
			System.out.println("Incorrect number of elements in RGBA array.");
			return -1;
		}
	}
	public static void viewImageData(int[][] imageTwoD) {
		if (imageTwoD.length > 3 && imageTwoD[0].length > 3) {
			int[][] rawPixels = new int[3][3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					rawPixels[i][j] = imageTwoD[i][j];
				}
			}
			System.out.println("Raw pixel data from the top left corner.");
			System.out.print(Arrays.deepToString(rawPixels).replace("],", "],\n") + "\n");
			int[][][] rgbPixels = new int[3][3][4];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					rgbPixels[i][j] = getRGBAFromPixel(imageTwoD[i][j]);
				}
			}
			System.out.println();
			System.out.println("Extracted RGBA pixel data from top the left corner.");
			for (int[][] row : rgbPixels) {
				System.out.print(Arrays.deepToString(row) + System.lineSeparator());
			}
		} else {
			System.out.println("The image is not large enough to extract 9 pixels from the top left corner");
		}
	}
}