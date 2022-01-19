Android development
===============
Public repository
![TheMaster](https://github.com/Mawuli87/android/blob/master/WordBF/jesus.jpg)

It uses a BitmapShader and **does not**:
* create a copy of the original bitmap
* use a clipPath (which is neither hardware accelerated nor anti-aliased)
* use setXfermode to clip the bitmap (which means drawing twice to the canvas)