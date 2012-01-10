package com.chenfm;

import com.iteye.tianshi.core.util.SequenceAchieve;

public class test {
	public static void main(String[] args) throws Exception {
		SequenceAchieve sequenceAchieve = SequenceAchieve.getSequence();
		String ss = sequenceAchieve.getDistributorCode();
		System.out.println(new String(ss));
	}
}
