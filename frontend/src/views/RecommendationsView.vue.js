import { ref } from 'vue';
import { ElMessage } from 'element-plus';
import { api } from '@/api';
const list = ref([]);
const loading = ref(false);
const touched = ref(false);
const refresh = async () => {
    touched.value = true;
    loading.value = true;
    try {
        const data = await api.recommendations({ size: 5 });
        list.value = data.list || [];
    }
    catch (e) {
        ElMessage.error(e?.message || '生成推荐失败');
    }
    finally {
        loading.value = false;
    }
};
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
const __VLS_5 = {}.ElButton;
/** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
// @ts-ignore
const __VLS_6 = __VLS_asFunctionalComponent(__VLS_5, new __VLS_5({
    ...{ 'onClick': {} },
    type: "primary",
    loading: (__VLS_ctx.loading),
}));
const __VLS_7 = __VLS_6({
    ...{ 'onClick': {} },
    type: "primary",
    loading: (__VLS_ctx.loading),
}, ...__VLS_functionalComponentArgsRest(__VLS_6));
let __VLS_9;
let __VLS_10;
let __VLS_11;
const __VLS_12 = {
    onClick: (__VLS_ctx.refresh)
};
__VLS_8.slots.default;
(__VLS_ctx.loading ? 'AI智能分析中...' : '生成推荐');
var __VLS_8;
const __VLS_13 = {}.ElSkeleton;
/** @type {[typeof __VLS_components.ElSkeleton, typeof __VLS_components.elSkeleton, typeof __VLS_components.ElSkeleton, typeof __VLS_components.elSkeleton, ]} */ ;
// @ts-ignore
const __VLS_14 = __VLS_asFunctionalComponent(__VLS_13, new __VLS_13({
    loading: (__VLS_ctx.loading),
    animated: true,
    rows: (4),
    ...{ style: {} },
}));
const __VLS_15 = __VLS_14({
    loading: (__VLS_ctx.loading),
    animated: true,
    rows: (4),
    ...{ style: {} },
}, ...__VLS_functionalComponentArgsRest(__VLS_14));
__VLS_16.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_16.slots;
    if (!__VLS_ctx.touched) {
        const __VLS_17 = {}.ElAlert;
        /** @type {[typeof __VLS_components.ElAlert, typeof __VLS_components.elAlert, ]} */ ;
        // @ts-ignore
        const __VLS_18 = __VLS_asFunctionalComponent(__VLS_17, new __VLS_17({
            title: "点击“生成推荐”后再生成结果。",
            type: "info",
            showIcon: true,
            closable: (false),
            ...{ style: {} },
        }));
        const __VLS_19 = __VLS_18({
            title: "点击“生成推荐”后再生成结果。",
            type: "info",
            showIcon: true,
            closable: (false),
            ...{ style: {} },
        }, ...__VLS_functionalComponentArgsRest(__VLS_18));
    }
    if (__VLS_ctx.touched && !__VLS_ctx.loading && __VLS_ctx.list.length === 0) {
        const __VLS_21 = {}.ElAlert;
        /** @type {[typeof __VLS_components.ElAlert, typeof __VLS_components.elAlert, ]} */ ;
        // @ts-ignore
        const __VLS_22 = __VLS_asFunctionalComponent(__VLS_21, new __VLS_21({
            title: "暂无推荐数据：请先做题并产生错题记录后再生成推荐。",
            type: "info",
            showIcon: true,
            closable: (false),
            ...{ style: {} },
        }));
        const __VLS_23 = __VLS_22({
            title: "暂无推荐数据：请先做题并产生错题记录后再生成推荐。",
            type: "info",
            showIcon: true,
            closable: (false),
            ...{ style: {} },
        }, ...__VLS_functionalComponentArgsRest(__VLS_22));
    }
    const __VLS_25 = {}.ElTable;
    /** @type {[typeof __VLS_components.ElTable, typeof __VLS_components.elTable, typeof __VLS_components.ElTable, typeof __VLS_components.elTable, ]} */ ;
    // @ts-ignore
    const __VLS_26 = __VLS_asFunctionalComponent(__VLS_25, new __VLS_25({
        data: (__VLS_ctx.list),
        ...{ style: {} },
    }));
    const __VLS_27 = __VLS_26({
        data: (__VLS_ctx.list),
        ...{ style: {} },
    }, ...__VLS_functionalComponentArgsRest(__VLS_26));
    __VLS_28.slots.default;
    const __VLS_29 = {}.ElTableColumn;
    /** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
    // @ts-ignore
    const __VLS_30 = __VLS_asFunctionalComponent(__VLS_29, new __VLS_29({
        prop: "problemId",
        label: "题目ID",
        width: "100",
    }));
    const __VLS_31 = __VLS_30({
        prop: "problemId",
        label: "题目ID",
        width: "100",
    }, ...__VLS_functionalComponentArgsRest(__VLS_30));
    const __VLS_33 = {}.ElTableColumn;
    /** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
    // @ts-ignore
    const __VLS_34 = __VLS_asFunctionalComponent(__VLS_33, new __VLS_33({
        prop: "title",
        label: "题目",
        minWidth: "220",
    }));
    const __VLS_35 = __VLS_34({
        prop: "title",
        label: "题目",
        minWidth: "220",
    }, ...__VLS_functionalComponentArgsRest(__VLS_34));
    const __VLS_37 = {}.ElTableColumn;
    /** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
    // @ts-ignore
    const __VLS_38 = __VLS_asFunctionalComponent(__VLS_37, new __VLS_37({
        prop: "difficulty",
        label: "难度",
        width: "120",
    }));
    const __VLS_39 = __VLS_38({
        prop: "difficulty",
        label: "难度",
        width: "120",
    }, ...__VLS_functionalComponentArgsRest(__VLS_38));
    const __VLS_41 = {}.ElTableColumn;
    /** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
    // @ts-ignore
    const __VLS_42 = __VLS_asFunctionalComponent(__VLS_41, new __VLS_41({
        prop: "reason",
        label: "推荐理由",
        minWidth: "320",
    }));
    const __VLS_43 = __VLS_42({
        prop: "reason",
        label: "推荐理由",
        minWidth: "320",
    }, ...__VLS_functionalComponentArgsRest(__VLS_42));
    const __VLS_45 = {}.ElTableColumn;
    /** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
    // @ts-ignore
    const __VLS_46 = __VLS_asFunctionalComponent(__VLS_45, new __VLS_45({
        label: "学习资源",
        minWidth: "260",
    }));
    const __VLS_47 = __VLS_46({
        label: "学习资源",
        minWidth: "260",
    }, ...__VLS_functionalComponentArgsRest(__VLS_46));
    __VLS_48.slots.default;
    {
        const { default: __VLS_thisSlot } = __VLS_48.slots;
        const [scope] = __VLS_getSlotParams(__VLS_thisSlot);
        if (scope.row.resourceUrl) {
            __VLS_asFunctionalElement(__VLS_intrinsicElements.a, __VLS_intrinsicElements.a)({
                href: (scope.row.resourceUrl),
                target: "_blank",
                rel: "noopener noreferrer",
            });
            (scope.row.resourceTitle || scope.row.resourceUrl);
            const __VLS_49 = {}.ElTag;
            /** @type {[typeof __VLS_components.ElTag, typeof __VLS_components.elTag, typeof __VLS_components.ElTag, typeof __VLS_components.elTag, ]} */ ;
            // @ts-ignore
            const __VLS_50 = __VLS_asFunctionalComponent(__VLS_49, new __VLS_49({
                size: "small",
                ...{ style: {} },
            }));
            const __VLS_51 = __VLS_50({
                size: "small",
                ...{ style: {} },
            }, ...__VLS_functionalComponentArgsRest(__VLS_50));
            __VLS_52.slots.default;
            (scope.row.resourceType || 'resource');
            var __VLS_52;
        }
        else {
            __VLS_asFunctionalElement(__VLS_intrinsicElements.span, __VLS_intrinsicElements.span)({});
        }
    }
    var __VLS_48;
    const __VLS_53 = {}.ElTableColumn;
    /** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
    // @ts-ignore
    const __VLS_54 = __VLS_asFunctionalComponent(__VLS_53, new __VLS_53({
        prop: "score",
        label: "分数",
        width: "100",
    }));
    const __VLS_55 = __VLS_54({
        prop: "score",
        label: "分数",
        width: "100",
    }, ...__VLS_functionalComponentArgsRest(__VLS_54));
    var __VLS_28;
}
var __VLS_16;
var __VLS_3;
var __VLS_dollars;
const __VLS_self = (await import('vue')).defineComponent({
    setup() {
        return {
            list: list,
            loading: loading,
            touched: touched,
            refresh: refresh,
        };
    },
});
export default (await import('vue')).defineComponent({
    setup() {
        return {};
    },
});
; /* PartiallyEnd: #4569/main.vue */
