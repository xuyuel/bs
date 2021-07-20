package com.sjzc.kt.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestChoiceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TestChoiceExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andChoiceIdIsNull() {
            addCriterion("choice_id is null");
            return (Criteria) this;
        }

        public Criteria andChoiceIdIsNotNull() {
            addCriterion("choice_id is not null");
            return (Criteria) this;
        }

        public Criteria andChoiceIdEqualTo(Integer value) {
            addCriterion("choice_id =", value, "choiceId");
            return (Criteria) this;
        }

        public Criteria andChoiceIdNotEqualTo(Integer value) {
            addCriterion("choice_id <>", value, "choiceId");
            return (Criteria) this;
        }

        public Criteria andChoiceIdGreaterThan(Integer value) {
            addCriterion("choice_id >", value, "choiceId");
            return (Criteria) this;
        }

        public Criteria andChoiceIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("choice_id >=", value, "choiceId");
            return (Criteria) this;
        }

        public Criteria andChoiceIdLessThan(Integer value) {
            addCriterion("choice_id <", value, "choiceId");
            return (Criteria) this;
        }

        public Criteria andChoiceIdLessThanOrEqualTo(Integer value) {
            addCriterion("choice_id <=", value, "choiceId");
            return (Criteria) this;
        }

        public Criteria andChoiceIdIn(List<Integer> values) {
            addCriterion("choice_id in", values, "choiceId");
            return (Criteria) this;
        }

        public Criteria andChoiceIdNotIn(List<Integer> values) {
            addCriterion("choice_id not in", values, "choiceId");
            return (Criteria) this;
        }

        public Criteria andChoiceIdBetween(Integer value1, Integer value2) {
            addCriterion("choice_id between", value1, value2, "choiceId");
            return (Criteria) this;
        }

        public Criteria andChoiceIdNotBetween(Integer value1, Integer value2) {
            addCriterion("choice_id not between", value1, value2, "choiceId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdIsNull() {
            addCriterion("question_id is null");
            return (Criteria) this;
        }

        public Criteria andQuestionIdIsNotNull() {
            addCriterion("question_id is not null");
            return (Criteria) this;
        }

        public Criteria andQuestionIdEqualTo(Integer value) {
            addCriterion("question_id =", value, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdNotEqualTo(Integer value) {
            addCriterion("question_id <>", value, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdGreaterThan(Integer value) {
            addCriterion("question_id >", value, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("question_id >=", value, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdLessThan(Integer value) {
            addCriterion("question_id <", value, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdLessThanOrEqualTo(Integer value) {
            addCriterion("question_id <=", value, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdIn(List<Integer> values) {
            addCriterion("question_id in", values, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdNotIn(List<Integer> values) {
            addCriterion("question_id not in", values, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdBetween(Integer value1, Integer value2) {
            addCriterion("question_id between", value1, value2, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("question_id not between", value1, value2, "questionId");
            return (Criteria) this;
        }

        public Criteria andChoiceContentIsNull() {
            addCriterion("choice_content is null");
            return (Criteria) this;
        }

        public Criteria andChoiceContentIsNotNull() {
            addCriterion("choice_content is not null");
            return (Criteria) this;
        }

        public Criteria andChoiceContentEqualTo(String value) {
            addCriterion("choice_content =", value, "choiceContent");
            return (Criteria) this;
        }

        public Criteria andChoiceContentNotEqualTo(String value) {
            addCriterion("choice_content <>", value, "choiceContent");
            return (Criteria) this;
        }

        public Criteria andChoiceContentGreaterThan(String value) {
            addCriterion("choice_content >", value, "choiceContent");
            return (Criteria) this;
        }

        public Criteria andChoiceContentGreaterThanOrEqualTo(String value) {
            addCriterion("choice_content >=", value, "choiceContent");
            return (Criteria) this;
        }

        public Criteria andChoiceContentLessThan(String value) {
            addCriterion("choice_content <", value, "choiceContent");
            return (Criteria) this;
        }

        public Criteria andChoiceContentLessThanOrEqualTo(String value) {
            addCriterion("choice_content <=", value, "choiceContent");
            return (Criteria) this;
        }

        public Criteria andChoiceContentLike(String value) {
            addCriterion("choice_content like", value, "choiceContent");
            return (Criteria) this;
        }

        public Criteria andChoiceContentNotLike(String value) {
            addCriterion("choice_content not like", value, "choiceContent");
            return (Criteria) this;
        }

        public Criteria andChoiceContentIn(List<String> values) {
            addCriterion("choice_content in", values, "choiceContent");
            return (Criteria) this;
        }

        public Criteria andChoiceContentNotIn(List<String> values) {
            addCriterion("choice_content not in", values, "choiceContent");
            return (Criteria) this;
        }

        public Criteria andChoiceContentBetween(String value1, String value2) {
            addCriterion("choice_content between", value1, value2, "choiceContent");
            return (Criteria) this;
        }

        public Criteria andChoiceContentNotBetween(String value1, String value2) {
            addCriterion("choice_content not between", value1, value2, "choiceContent");
            return (Criteria) this;
        }

        public Criteria andRightAnswerIsNull() {
            addCriterion("right_answer is null");
            return (Criteria) this;
        }

        public Criteria andRightAnswerIsNotNull() {
            addCriterion("right_answer is not null");
            return (Criteria) this;
        }

        public Criteria andRightAnswerEqualTo(Integer value) {
            addCriterion("right_answer =", value, "rightAnswer");
            return (Criteria) this;
        }

        public Criteria andRightAnswerNotEqualTo(Integer value) {
            addCriterion("right_answer <>", value, "rightAnswer");
            return (Criteria) this;
        }

        public Criteria andRightAnswerGreaterThan(Integer value) {
            addCriterion("right_answer >", value, "rightAnswer");
            return (Criteria) this;
        }

        public Criteria andRightAnswerGreaterThanOrEqualTo(Integer value) {
            addCriterion("right_answer >=", value, "rightAnswer");
            return (Criteria) this;
        }

        public Criteria andRightAnswerLessThan(Integer value) {
            addCriterion("right_answer <", value, "rightAnswer");
            return (Criteria) this;
        }

        public Criteria andRightAnswerLessThanOrEqualTo(Integer value) {
            addCriterion("right_answer <=", value, "rightAnswer");
            return (Criteria) this;
        }

        public Criteria andRightAnswerIn(List<Integer> values) {
            addCriterion("right_answer in", values, "rightAnswer");
            return (Criteria) this;
        }

        public Criteria andRightAnswerNotIn(List<Integer> values) {
            addCriterion("right_answer not in", values, "rightAnswer");
            return (Criteria) this;
        }

        public Criteria andRightAnswerBetween(Integer value1, Integer value2) {
            addCriterion("right_answer between", value1, value2, "rightAnswer");
            return (Criteria) this;
        }

        public Criteria andRightAnswerNotBetween(Integer value1, Integer value2) {
            addCriterion("right_answer not between", value1, value2, "rightAnswer");
            return (Criteria) this;
        }

        public Criteria andChoiceSortIsNull() {
            addCriterion("choice_sort is null");
            return (Criteria) this;
        }

        public Criteria andChoiceSortIsNotNull() {
            addCriterion("choice_sort is not null");
            return (Criteria) this;
        }

        public Criteria andChoiceSortEqualTo(Integer value) {
            addCriterion("choice_sort =", value, "choiceSort");
            return (Criteria) this;
        }

        public Criteria andChoiceSortNotEqualTo(Integer value) {
            addCriterion("choice_sort <>", value, "choiceSort");
            return (Criteria) this;
        }

        public Criteria andChoiceSortGreaterThan(Integer value) {
            addCriterion("choice_sort >", value, "choiceSort");
            return (Criteria) this;
        }

        public Criteria andChoiceSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("choice_sort >=", value, "choiceSort");
            return (Criteria) this;
        }

        public Criteria andChoiceSortLessThan(Integer value) {
            addCriterion("choice_sort <", value, "choiceSort");
            return (Criteria) this;
        }

        public Criteria andChoiceSortLessThanOrEqualTo(Integer value) {
            addCriterion("choice_sort <=", value, "choiceSort");
            return (Criteria) this;
        }

        public Criteria andChoiceSortIn(List<Integer> values) {
            addCriterion("choice_sort in", values, "choiceSort");
            return (Criteria) this;
        }

        public Criteria andChoiceSortNotIn(List<Integer> values) {
            addCriterion("choice_sort not in", values, "choiceSort");
            return (Criteria) this;
        }

        public Criteria andChoiceSortBetween(Integer value1, Integer value2) {
            addCriterion("choice_sort between", value1, value2, "choiceSort");
            return (Criteria) this;
        }

        public Criteria andChoiceSortNotBetween(Integer value1, Integer value2) {
            addCriterion("choice_sort not between", value1, value2, "choiceSort");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andDelStateIsNull() {
            addCriterion("del_state is null");
            return (Criteria) this;
        }

        public Criteria andDelStateIsNotNull() {
            addCriterion("del_state is not null");
            return (Criteria) this;
        }

        public Criteria andDelStateEqualTo(Integer value) {
            addCriterion("del_state =", value, "delState");
            return (Criteria) this;
        }

        public Criteria andDelStateNotEqualTo(Integer value) {
            addCriterion("del_state <>", value, "delState");
            return (Criteria) this;
        }

        public Criteria andDelStateGreaterThan(Integer value) {
            addCriterion("del_state >", value, "delState");
            return (Criteria) this;
        }

        public Criteria andDelStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("del_state >=", value, "delState");
            return (Criteria) this;
        }

        public Criteria andDelStateLessThan(Integer value) {
            addCriterion("del_state <", value, "delState");
            return (Criteria) this;
        }

        public Criteria andDelStateLessThanOrEqualTo(Integer value) {
            addCriterion("del_state <=", value, "delState");
            return (Criteria) this;
        }

        public Criteria andDelStateIn(List<Integer> values) {
            addCriterion("del_state in", values, "delState");
            return (Criteria) this;
        }

        public Criteria andDelStateNotIn(List<Integer> values) {
            addCriterion("del_state not in", values, "delState");
            return (Criteria) this;
        }

        public Criteria andDelStateBetween(Integer value1, Integer value2) {
            addCriterion("del_state between", value1, value2, "delState");
            return (Criteria) this;
        }

        public Criteria andDelStateNotBetween(Integer value1, Integer value2) {
            addCriterion("del_state not between", value1, value2, "delState");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}