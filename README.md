GetCMYKImage
============

Get images with CMYK colorspace (Images not supported by Android)

  Transform an image with CMYK colorspace to an image with RGB colorspace, that can be decoded by Android. Gets a drawable image (Bitmap) from a CMYK image. The image could be on Internet (URL), on the SD card (path), or on internal memory (path).
  
Examples of CMYK images:
    http://i.imgur.com/MPSIT.jpg
    http://ow.ly/rguro
    http://ow.ly/rguDt
    
These images cannot be displayed even with the Android web browser. They need to be encoded in RGB colorspace first.
