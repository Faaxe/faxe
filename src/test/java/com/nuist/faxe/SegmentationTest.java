package com.nuist.faxe;

import com.nuist.faxe.algorithm.segmentation.AudioSegmentation;
import com.nuist.faxe.algorithm.segmentation.WavCut;

import java.io.File;
import java.util.Map;

/**
 * @author ZhouXiang
 **/
public class SegmentationTest {
   public static void main(String[] args) throws Exception{
      WavCut.cut("C:\\Users\\Administrator\\Desktop\\前端\\A1b.wav",
              "C:\\Users\\Administrator\\Desktop\\前端\\A1b_01.wav",
              10,
              5);
   }
}
