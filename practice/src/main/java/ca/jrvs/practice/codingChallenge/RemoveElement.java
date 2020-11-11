package ca.jrvs.practice.codingChallenge;

/**
 * https://www.notion.so/Remove-Element-3f9f6190f9ae49ccb2b2be4b071b02bb
 */

public class RemoveElement {

    /**
     * Description: Remove all instances of the given value from an array and return new length
     * big O: O(n)
     * Justification: Iterates through the array
     */

    public int removeElement(int[] nums, int val){
        int i = 0;
        for (int j = 0; j<nums.length; j++){
            if (nums[j] != val){
                nums[i]=nums[j];
                i++;
            }
        }
        return i;
    }

}
