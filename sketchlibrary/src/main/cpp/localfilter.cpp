//
// Created by deejan on 4/24/18.
//

#include <jni.h>
#include <string>

static void hsl_rgb(unsigned int &c, const float &temp1, const float &temp2, const float &temp3) {
    if ((temp3 * 6) < 1)
        c = (unsigned int) ((temp2 + (temp1 - temp2) * 6 * temp3) * 100);
    else if ((temp3 * 2) < 1)
        c = (unsigned int) (temp1 * 100);
    else if ((temp3 * 3) < 2)
        c = (unsigned int) ((temp2 + (temp1 - temp2) * (.66666 - temp3) * 6) * 100);
    else
        c = (unsigned int) (temp2 * 100);
    return;
}

void saturation(int *pixels, float level, int width, int height) {
    unsigned int r;
    unsigned int g;
    unsigned int b;

    float temp1, temp2, temp3;

    float r_percent;
    float g_percent;
    float b_percent;

    float max_color, min_color;
    float L, S, H;

    for (int i = 0; i < width * height; i++) {
        r = (unsigned int) (pixels[i] >> 16) & 0xFF;
        g = (unsigned int) (pixels[i] >> 8) & 0xFF;
        b = (unsigned int) (pixels[i]) & 0xFF;

        r_percent = ((float) r) / 255;
        g_percent = ((float) g) / 255;
        b_percent = ((float) b) / 255;

        max_color = 0;
        if ((r_percent >= g_percent) && (r_percent >= b_percent)) {
            max_color = r_percent;
        }
        if ((g_percent >= r_percent) && (g_percent >= b_percent))
            max_color = g_percent;
        if ((b_percent >= r_percent) && (b_percent >= g_percent))
            max_color = b_percent;

        min_color = 0;
        if ((r_percent <= g_percent) && (r_percent <= b_percent))
            min_color = r_percent;
        if ((g_percent <= r_percent) && (g_percent <= b_percent))
            min_color = g_percent;
        if ((b_percent <= r_percent) && (b_percent <= g_percent))
            min_color = b_percent;

        L = 0;
        S = 0;
        H = 0;

        L = (max_color + min_color) / 2;

        if (max_color == min_color) {
            S = 0;
            H = 0;
        } else {
            if (L < .50) {
                S = (max_color - min_color) / (max_color + min_color);
            } else {
                S = (max_color - min_color) / (2 - max_color - min_color);
            }
            if (max_color == r_percent) {
                H = (g_percent - b_percent) / (max_color - min_color);
            }
            if (max_color == g_percent) {
                H = 2 + (b_percent - r_percent) / (max_color - min_color);
            }
            if (max_color == b_percent) {
                H = 4 + (r_percent - g_percent) / (max_color - min_color);
            }
        }
        S = (unsigned int) (S * 100);
        L = (unsigned int) (L * 100);
        H = H * 60;
        if (H < 0)
            H += 360;

        S *= level;
        if (S > 100) {
            S = 100;
        } else if (S < 0) {
            S = 0;
        }

        L = L / 100;
        S = S / 100;
        H = H / 360;

        if (S == 0) {
            r = static_cast<unsigned int>(L * 100);
            g = static_cast<unsigned int>(L * 100);
            b = static_cast<unsigned int>(L * 100);
        } else {
            temp1 = 0;
            if (L < .50) {
                temp1 = L * (1 + S);
            } else {
                temp1 = L + S - (L * S);
            }

            temp2 = 2 * L - temp1;

            for (int j = 0; j < 3; j++) {
                switch (j) {
                    case 0: // red
                    {
                        temp3 = H + .33333f;
                        if (temp3 > 1)
                            temp3 -= 1;
                        hsl_rgb(r, temp1, temp2, temp3);
                        break;
                    }
                    case 1: // green
                    {
                        temp3 = H;
                        hsl_rgb(g, temp1, temp2, temp3);
                        break;
                    }
                    case 2: // blue
                    {
                        temp3 = H - .33333f;
                        if (temp3 < 0)
                            temp3 += 1;
                        hsl_rgb(b, temp1, temp2, temp3);
                        break;
                    }
                    default: {
                    }
                }
            }
        }
        r = (unsigned int) ((((float) r) / 100) * 255);
        g = (unsigned int) ((((float) g) / 100) * 255);
        b = (unsigned int) ((((float) b) / 100) * 255);

        pixels[i] = pixels[i] & 0xFF000000 | ((int) r << 16) & 0x00FF0000 |
                    ((int) g << 8) & 0x0000FF00 |
                    (int) b & 0x000000FF;;

    }
}

static void
colorOverlay(int *pixels, int depth, float red, float green, float blue, int width, int height) {

    float R, G, B;

    for (int i = 0; i < width * height; i++) {
        R = (pixels[i] >> 16) & 0xFF;
        G = (pixels[i] >> 8) & 0xFF;
        B = (pixels[i]) & 0xFF;

        R += (depth * red);
        if (R > 255) { R = 255; }

        G += (depth * green);
        if (G > 255) { G = 255; }

        B += (depth * blue);
        if (B > 255) { B = 255; }

        pixels[i] = pixels[i] & 0xFF000000 | ((int) R << 16) & 0x00FF0000 |
                    ((int) G << 8) & 0x0000FF00 |
                    (int) B & 0x000000FF;
    }
}

static void contrast(int width, int height, int *pixels, float value) {

    float red, green, blue;
    int R, G, B;

    for (int i = 0; i < width * height; i++) {
        red = (pixels[i] >> 16) & 0xFF;
        green = (pixels[i] >> 8) & 0xFF;
        blue = (pixels[i]) & 0xFF;

        red = static_cast<float>(((((red / 255.0) - 0.5) * value) + 0.5) * 255.0);
        green = static_cast<float>(((((green / 255.0) - 0.5) * value) + 0.5) * 255.0);
        blue = static_cast<float>(((((blue / 255.0) - 0.5) * value) + 0.5) * 255.0);

        // validation check
        if (red > 255)
            red = 255;
        else if (red < 0)
            red = 0;

        if (green > 255)
            green = 255;
        else if (green < 0)
            green = 0;

        if (blue > 255)
            blue = 255;
        else if (blue < 0)
            blue = 0;

        R = (int) red;
        G = (int) green;
        B = (int) blue;
        pixels[i] = pixels[i] & 0xFF000000 | (R << 16) & 0x00FF0000 | (G << 8) & 0x0000FF00 |
                    B & 0x000000FF;
    }
}

static void brightness(int width, int height, int *pixels, int value) {

    int red, green, blue;

    for (int i = 0; i < width * height; i++) {
        red = (pixels[i] >> 16) & 0xFF;
        green = (pixels[i] >> 8) & 0xFF;
        blue = (pixels[i]) & 0xFF;

        red += value;
        green += value;
        blue += value;

        // validation check
        if (red > 255)
            red = 255;
        else if (red < 0)
            red = 0;

        if (green > 255)
            green = 255;
        else if (green < 0)
            green = 0;

        if (blue > 255)
            blue = 255;
        else if (blue < 0)
            blue = 0;

        pixels[i] = pixels[i] & 0xFF000000 | (red << 16) & 0x00FF0000 | (green << 8) & 0x0000FF00 |
                    blue & 0x000000FF;
    }
}


static void applyChannelCurves(int width, int height, int *pixels, int *r, int *g, int *b) {
    int red;
    int green;
    int blue;
    int alpha;

    for (int i = 0; i < width * height; i++) {
        if (r != NULL)
            red = (r[(pixels[i] >> 16) & 0xFF] << 16) & 0x00FF0000;
        else
            red = (pixels[i] << 16) & 0x00FF0000;

        if (g != NULL)
            green = (g[(pixels[i] >> 8) & 0xFF] << 8) & 0x0000FF00;
        else
            green = (pixels[i] << 8) & 0x0000FF00;

        if (b != NULL)
            blue = b[pixels[i] & 0xFF] & 0x000000FF;
        else
            blue = pixels[i] & 0x000000FF;

        alpha = pixels[i] & 0xFF000000;

        pixels[i] = alpha | red | green | blue;
    }
}

static void applyRGBCurve(int width, int height, int *pixels, int *rgb) {
    int R[256];
    int G[256];
    int B[256];
    for (int i = 0; i < 256; i++) {
        R[i] = (rgb[i] << 16) & 0x00FF0000;
        G[i] = (rgb[i] << 8) & 0x0000FF00;
        B[i] = rgb[i] & 0x000000FF;
    }

    for (int i = 0; i < width * height; i++) {
        pixels[i] =
                0xFF000000 & pixels[i] | R[(pixels[i] >> 16) & 0xFF] | G[(pixels[i] >> 8) & 0xFF] |
                B[pixels[i] & 0xFF];
    }

}

