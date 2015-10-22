package de.t7n.Ambiance

object RgbHsv {
  /** Converts an rgb triplet where each value is in the range [0,1]
    *  into an hsv triplet where h (hue) is in the range [0,360], s (saturation)
    *  and value in the range [0,1]
    */
  def rgbToHsv(r:Double, g:Double, b:Double):(Double, Double, Double) = {
    //get the value of the strongest color component
    val M = (r max g) max b
    //get the value of the weakest color component
    val m = (r min g) min b
    //this is already a first measure of how "colorful" the color is
    //this is not yet the saturation but it is a measure of how 
    //dominant the max color component is
    val c = M-m
    //determine the position in the hsv color circle 
    //we check which third of the color circle we 
    //lie in by comparing to the three rgb components
    val h1 = if      (c==0.0) 0.0    //we have some grey tone
    else if (M==r)  ((g-b)/c) % 6
    else if (M==g)  ((b-r)/c) + 2.0
    else   /*M==b*/ ((r-g)/c) + 4.0
    //convert to degrees. remove this step if you want 
    //the h value in the range [0,1] as well
    //(remember to adjust the hsvToRgb function if you do that!)
    val h = h1*60.0
    val v = M
    //if we have a greyish tone, saturation is zero
    //same is true if the value is zero, then we have black
    val s = if (c==0.0 || v==0.0) 0.0
    else c / v

    (h,s,v)
  }

  /** Converts an hsv triplet in the range ([0,360],[0,1],[0,1])
    *  to an rgb triplet in the range ([0,1],[0,1],[0,1])
    */
  def hsvToRgb(h:Double, s:Double, v:Double)
  :(Double, Double, Double) = {
    //restore the "distance" between the max and min RGB component
    //this is simply the reverse of the calculation for s
    val c  = s*v
    //normalize the degree value for the hue to [0,1]
    //omit this step if you input h in the range [0,1] already
    val h1 = h / 60.0
    //restore one rgb value (we don't know which it is yet, hence "x")
    val x  = c*(1.0 - ((h1 % 2) - 1.0).abs)
    //depending on where we lie in the color circle, restore a partial rgb triplet 
    val (r,g,b) = if      (h1 < 1.0) (c, x, 0.0)
    else if (h1 < 2.0) (x, c, 0.0)
    else if (h1 < 3.0) (0.0, c, x)
    else if (h1 < 4.0) (0.0, x, c)
    else if (h1 < 5.0) (x, 0.0, c)
    else  /*h1 < 6.0*/ (c, 0.0, x)
    //this is the smallest component. 
    val m = v-c
    //we offset all components by m.
    //the zero component is now set to the correct minimum
    //the other two are shifted to the final correct value
    (r+m, g+m, b+m)
  }


  def hsv2Rgb(hue: Double, saturation: Double, value:Double): (Double, Double, Double) = {

    var h : Double = (hue * 6.0);
    var f : Double = hue * 6.0 - h;
    var p : Double = value * (1.0 - saturation);
    var q : Double = value * (1.0 - f * saturation);
    var t : Double = value * (1.0 - (1 - f) * saturation);
    println(value)
    if(h<1.0) return (value, t, p)
    else if(h<2.0) return (q, value, p);
    else if(h<3.0) return (p, value, t);
    else if(h<4.0) return (p, q, value);
    else if(h<5.0) return (t, p, value);
    else return (value, p, q);

  }
}