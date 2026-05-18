import { onMounted, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { api } from '@/api';
const list = ref([]);
const page = ref(1);
const size = ref(20);
const total = ref(0);
const load = async () => {
    const data = await api.favorites({ page: page.value, size: size.value });
    list.value = data.list || [];
    total.value = data.total || 0;
};
const onSizeChange = async () => { page.value = 1; await load(); };
const remove = async (id) => {
    await api.unfavorite(id);
    ElMessage.success('已取消收藏');
    await load();
};
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
    prop: "problemId",
    label: "题目ID",
    width: "100",
}));
const __VLS_11 = __VLS_10({
    prop: "problemId",
    label: "题目ID",
    width: "100",
}, ...__VLS_functionalComponentArgsRest(__VLS_10));
const __VLS_13 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_14 = __VLS_asFunctionalComponent(__VLS_13, new __VLS_13({
    prop: "problemTitle",
    label: "题目",
}));
const __VLS_15 = __VLS_14({
    prop: "problemTitle",
    label: "题目",
}, ...__VLS_functionalComponentArgsRest(__VLS_14));
const __VLS_17 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_18 = __VLS_asFunctionalComponent(__VLS_17, new __VLS_17({
    prop: "difficulty",
    label: "难度",
    width: "120",
}));
const __VLS_19 = __VLS_18({
    prop: "difficulty",
    label: "难度",
    width: "120",
}, ...__VLS_functionalComponentArgsRest(__VLS_18));
const __VLS_21 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_22 = __VLS_asFunctionalComponent(__VLS_21, new __VLS_21({
    prop: "favoritedAt",
    label: "收藏时间",
    width: "200",
}));
const __VLS_23 = __VLS_22({
    prop: "favoritedAt",
    label: "收藏时间",
    width: "200",
}, ...__VLS_functionalComponentArgsRest(__VLS_22));
const __VLS_25 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_26 = __VLS_asFunctionalComponent(__VLS_25, new __VLS_25({
    label: "操作",
    width: "140",
}));
const __VLS_27 = __VLS_26({
    label: "操作",
    width: "140",
}, ...__VLS_functionalComponentArgsRest(__VLS_26));
__VLS_28.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_28.slots;
    const [scope] = __VLS_getSlotParams(__VLS_thisSlot);
    const __VLS_29 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_30 = __VLS_asFunctionalComponent(__VLS_29, new __VLS_29({
        ...{ 'onClick': {} },
        link: true,
        type: "primary",
    }));
    const __VLS_31 = __VLS_30({
        ...{ 'onClick': {} },
        link: true,
        type: "primary",
    }, ...__VLS_functionalComponentArgsRest(__VLS_30));
    let __VLS_33;
    let __VLS_34;
    let __VLS_35;
    const __VLS_36 = {
        onClick: (...[$event]) => {
            __VLS_ctx.$router.push(`/problems/${scope.row.problemId}`);
        }
    };
    __VLS_32.slots.default;
    var __VLS_32;
    const __VLS_37 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_38 = __VLS_asFunctionalComponent(__VLS_37, new __VLS_37({
        ...{ 'onClick': {} },
        link: true,
        type: "danger",
    }));
    const __VLS_39 = __VLS_38({
        ...{ 'onClick': {} },
        link: true,
        type: "danger",
    }, ...__VLS_functionalComponentArgsRest(__VLS_38));
    let __VLS_41;
    let __VLS_42;
    let __VLS_43;
    const __VLS_44 = {
        onClick: (...[$event]) => {
            __VLS_ctx.remove(scope.row.problemId);
        }
    };
    __VLS_40.slots.default;
    var __VLS_40;
}
var __VLS_28;
var __VLS_8;
__VLS_asFunctionalElement(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({
    ...{ style: {} },
});
const __VLS_45 = {}.ElPagination;
/** @type {[typeof __VLS_components.ElPagination, typeof __VLS_components.elPagination, ]} */ ;
// @ts-ignore
const __VLS_46 = __VLS_asFunctionalComponent(__VLS_45, new __VLS_45({
    ...{ 'onCurrentChange': {} },
    ...{ 'onSizeChange': {} },
    currentPage: (__VLS_ctx.page),
    pageSize: (__VLS_ctx.size),
    layout: "total, sizes, prev, pager, next, jumper",
    pageSizes: ([10, 20, 50]),
    total: (__VLS_ctx.total),
}));
const __VLS_47 = __VLS_46({
    ...{ 'onCurrentChange': {} },
    ...{ 'onSizeChange': {} },
    currentPage: (__VLS_ctx.page),
    pageSize: (__VLS_ctx.size),
    layout: "total, sizes, prev, pager, next, jumper",
    pageSizes: ([10, 20, 50]),
    total: (__VLS_ctx.total),
}, ...__VLS_functionalComponentArgsRest(__VLS_46));
let __VLS_49;
let __VLS_50;
let __VLS_51;
const __VLS_52 = {
    onCurrentChange: (__VLS_ctx.load)
};
const __VLS_53 = {
    onSizeChange: (__VLS_ctx.onSizeChange)
};
var __VLS_48;
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
            remove: remove,
        };
    },
});
export default (await import('vue')).defineComponent({
    setup() {
        return {};
    },
});
; /* PartiallyEnd: #4569/main.vue */
