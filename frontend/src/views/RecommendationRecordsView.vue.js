import { onMounted, ref } from 'vue';
import { api } from '@/api';
const list = ref([]);
const page = ref(1);
const size = ref(20);
const total = ref(0);
const load = async () => {
    const data = await api.recommendRecords({ page: page.value, size: size.value });
    list.value = data.list || [];
    total.value = data.total || 0;
};
const onSizeChange = async () => { page.value = 1; await load(); };
onMounted(load);
debugger; /* PartiallyEnd: #3632/scriptSetup.vue */
const __VLS_ctx = {};
let __VLS_components;
let __VLS_directives;
const __VLS_0 = {}.ElCard;
/** @type {[typeof __VLS_components.ElCard, typeof __VLS_components.elCard, typeof __VLS_components.ElCard, typeof __VLS_components.elCard, ]} */ ;
// @ts-ignore
const __VLS_1 = __VLS_asFunctionalComponent(__VLS_0, new __VLS_0({}));
const __VLS_2 = __VLS_1({}, ...__VLS_functionalComponentArgsRest(__VLS_1));
var __VLS_4 = {};
__VLS_3.slots.default;
const __VLS_5 = {}.ElTable;
/** @type {[typeof __VLS_components.ElTable, typeof __VLS_components.elTable, typeof __VLS_components.ElTable, typeof __VLS_components.elTable, ]} */ ;
// @ts-ignore
const __VLS_6 = __VLS_asFunctionalComponent(__VLS_5, new __VLS_5({
    data: (__VLS_ctx.list),
}));
const __VLS_7 = __VLS_6({
    data: (__VLS_ctx.list),
}, ...__VLS_functionalComponentArgsRest(__VLS_6));
__VLS_8.slots.default;
const __VLS_9 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_10 = __VLS_asFunctionalComponent(__VLS_9, new __VLS_9({
    prop: "id",
    label: "记录ID",
    width: "90",
}));
const __VLS_11 = __VLS_10({
    prop: "id",
    label: "记录ID",
    width: "90",
}, ...__VLS_functionalComponentArgsRest(__VLS_10));
const __VLS_13 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_14 = __VLS_asFunctionalComponent(__VLS_13, new __VLS_13({
    prop: "problemId",
    label: "题目ID",
    width: "90",
}));
const __VLS_15 = __VLS_14({
    prop: "problemId",
    label: "题目ID",
    width: "90",
}, ...__VLS_functionalComponentArgsRest(__VLS_14));
const __VLS_17 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_18 = __VLS_asFunctionalComponent(__VLS_17, new __VLS_17({
    prop: "problemTitle",
    label: "题目",
    minWidth: "220",
}));
const __VLS_19 = __VLS_18({
    prop: "problemTitle",
    label: "题目",
    minWidth: "220",
}, ...__VLS_functionalComponentArgsRest(__VLS_18));
const __VLS_21 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_22 = __VLS_asFunctionalComponent(__VLS_21, new __VLS_21({
    prop: "difficulty",
    label: "难度",
    width: "100",
}));
const __VLS_23 = __VLS_22({
    prop: "difficulty",
    label: "难度",
    width: "100",
}, ...__VLS_functionalComponentArgsRest(__VLS_22));
const __VLS_25 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_26 = __VLS_asFunctionalComponent(__VLS_25, new __VLS_25({
    prop: "reasonText",
    label: "推荐理由",
    minWidth: "280",
}));
const __VLS_27 = __VLS_26({
    prop: "reasonText",
    label: "推荐理由",
    minWidth: "280",
}, ...__VLS_functionalComponentArgsRest(__VLS_26));
const __VLS_29 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_30 = __VLS_asFunctionalComponent(__VLS_29, new __VLS_29({
    prop: "score",
    label: "分数",
    width: "100",
}));
const __VLS_31 = __VLS_30({
    prop: "score",
    label: "分数",
    width: "100",
}, ...__VLS_functionalComponentArgsRest(__VLS_30));
const __VLS_33 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_34 = __VLS_asFunctionalComponent(__VLS_33, new __VLS_33({
    prop: "recommendedAt",
    label: "推荐时间",
    width: "180",
}));
const __VLS_35 = __VLS_34({
    prop: "recommendedAt",
    label: "推荐时间",
    width: "180",
}, ...__VLS_functionalComponentArgsRest(__VLS_34));
var __VLS_8;
__VLS_asFunctionalElement(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({
    ...{ style: {} },
});
const __VLS_37 = {}.ElPagination;
/** @type {[typeof __VLS_components.ElPagination, typeof __VLS_components.elPagination, ]} */ ;
// @ts-ignore
const __VLS_38 = __VLS_asFunctionalComponent(__VLS_37, new __VLS_37({
    ...{ 'onCurrentChange': {} },
    ...{ 'onSizeChange': {} },
    currentPage: (__VLS_ctx.page),
    pageSize: (__VLS_ctx.size),
    layout: "total, sizes, prev, pager, next, jumper",
    pageSizes: ([10, 20, 50]),
    total: (__VLS_ctx.total),
}));
const __VLS_39 = __VLS_38({
    ...{ 'onCurrentChange': {} },
    ...{ 'onSizeChange': {} },
    currentPage: (__VLS_ctx.page),
    pageSize: (__VLS_ctx.size),
    layout: "total, sizes, prev, pager, next, jumper",
    pageSizes: ([10, 20, 50]),
    total: (__VLS_ctx.total),
}, ...__VLS_functionalComponentArgsRest(__VLS_38));
let __VLS_41;
let __VLS_42;
let __VLS_43;
const __VLS_44 = {
    onCurrentChange: (__VLS_ctx.load)
};
const __VLS_45 = {
    onSizeChange: (__VLS_ctx.onSizeChange)
};
var __VLS_40;
var __VLS_3;
var __VLS_dollars;
const __VLS_self = (await import('vue')).defineComponent({
    setup() {
        return {
            list: list,
            page: page,
            size: size,
            total: total,
            load: load,
            onSizeChange: onSizeChange,
        };
    },
});
export default (await import('vue')).defineComponent({
    setup() {
        return {};
    },
});
; /* PartiallyEnd: #4569/main.vue */
