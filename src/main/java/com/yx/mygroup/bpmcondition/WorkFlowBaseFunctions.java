package com.yx.mygroup.bpmcondition;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 工作流特有分支函数定义类 ，函数说明配置在formula-functions.xml
 *
 * @author yuanxin
 */
@Slf4j
public class WorkFlowBaseFunctions {

    /**
     * bpm函数分类的key
     */
    private static final int BPM_WORKFLOW_FUNCTION = 1001;

    /**
     * 判断两个集合是否相等
     *
     * @param varList1 集合1
     * @param varList2 集合2
     * @return true/false
     */
    private static boolean compareListEquals(List<?> varList1, List<?> varList2) {
        Set<?> varSet1 = buildSet(varList1);
        Set<?> varSet2 = buildSet(varList2);
        if (varSet1.size() != varSet2.size()) {
            return false;
        }
        for (Object var : varSet1) {
            boolean contains = varList2.contains(var);
            if (!contains) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断两个集合是否不相等
     *
     * @param varList1 集合1
     * @param varList2 集合2
     * @return true/false
     */
    private static boolean compareListNotEquals(List<?> varList1, List<?> varList2) {
        Set<?> varSet1 = buildSet(varList1);
        Set<?> varSet2 = buildSet(varList2);
        if (varSet1.size() != varSet2.size()) {
            return true;
        }
        for (Object var : varSet1) {
            boolean contains = varList2.contains(var);
            if (!contains) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断集合varList1是否属于集合varList2
     *
     * @param varList1 集合1
     * @param varList2 集合2
     * @return true/false
     */
    private static boolean compareListIn(List<?> varList1, List<?> varList2) {
        Set<?> varSet1 = buildSet(varList1);
        Set<?> varSet2 = buildSet(varList2);
        if (varSet1.size() == 0) {
            return true;
        }
        for (Object var : varSet1) {
            boolean contains = varSet2.contains(var);
            if (!contains) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断集合varList1是否包含varList2的所有值
     *
     * @param varList1 集合1
     * @param varList2 集合2
     * @return true/false
     */
    private static boolean compareListIncludeAll(List<?> varList1, List<?> varList2) {
        Set<?> varSet1 = buildSet(varList1);
        Set<?> varSet2 = buildSet(varList2);
        if (varList2.size() == 0) {
            return true;
        }
        for (Object var : varSet2) {
            boolean contains = !varSet1.contains(var);
            if (contains) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断集合varList1是否包含varList2的任一值
     *
     * @param varList1 集合1
     * @param varList2 集合2
     * @return true/false
     */
    private static boolean compareListIncludeAny(List<?> varList1, List<?> varList2) {
        Set<?> varSet1 = buildSet(varList1);
        Set<?> varSet2 = buildSet(varList2);
        return compareSetIncludeAny(varSet1, varSet2);
    }

    private static Set<?> buildSet(List<?> varList) {
        Set<?> varSet;
        if (varList == null) {
            varSet = new HashSet<>();
        } else {
            varSet = new HashSet<>(varList);
        }
        return varSet;
    }

    public static boolean compareSetIncludeAny(Set<?> varSet1, Set<?> varSet2) {
        if (varSet2.size() == 0) {
            return true;
        }
        for (Object var : varSet2) {
            boolean contains = varSet1.contains(var);
            if (contains) {
                return true;
            }
        }
        return false;
    }


    //////////////////////////////////////////////////////////////人员函数///////////////////////////////////////////////////////////////////

    public static boolean bpmEquals(Object param1, Object param2) {
        if (param1 == null && param2 == null) {
            return true;
        } else if (param1 == null || param2 == null) {
            return false;
        } else if (param1 instanceof String && param2 instanceof String) {
            return param1.equals(param2);
        } else {
            List<Object> paramList1;
            if (param1 instanceof Collection) {
                paramList1 = new ArrayList<>((Collection<?>) param1);
            } else {
                paramList1 = Collections.singletonList(param1);
            }
            List<Object> paramList2;
            if (param2 instanceof Collection) {
                paramList2 = new ArrayList<>((Collection<?>) param2);
            } else {
                paramList2 = Collections.singletonList(param2);
            }
            return compareListEquals(paramList1, paramList2);
        }
    }

//    public static boolean bpmNotEquals(Object param1, Object param2) {
//        return compareListNotEquals(ids1, ids2);
//    }
//
//    public static boolean bpmIn(Object param1, Object param2) {
//        return compareListIn(ids1, ids2);
//    }
//
//    public static boolean bpmIncludeAll(Object param1, Object param2) {
//        return compareListIncludeAll(ids1, ids2);
//    }
//
//    public static boolean bpmIncludeAny(Object param1, Object param2) {
//        return compareListIncludeAny(ids1, ids2);
//    }

}
