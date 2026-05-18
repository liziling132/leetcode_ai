import { onMounted, reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { api } from '@/api';
const stats = reactive({ totalSubmissions: 0, acceptedSubmissions: 0, acceptanceRate: 0, recent7Days: [] });
const weakPoints = ref([]);
const adviceLoading = ref(false);
const adviceText = ref('');
const adviceSource = ref('');
const loadStats = async () => {
    const s = await api.stats();
    const w = await api.weakPoints({ size: 8 });
    Object.assign(stats, s || {});
    weakPoints.value = (w?.list || []);
};
const loadAdvice = async () => {
    adviceLoading.value = true;
    try {
        const a = await api.learningAdvice();
        adviceText.value = a.advice || '';
        adviceSource.value = a.aiSource || 'RULE';
    }
    catch (e) {
        ElMessage.error(e?.message || '获取学习建议失败');
    }
    finally {
        adviceLoading.value = false;
    }
};
onMounted(async () => {
    await loadStats();
    await loadAdvice();
});
debugger; /* PartiallyEnd: #3632/scriptSetup.vue */
const __VLS_ctx = {};
let __VLS_components;
let __VLS_directives;
const __VLS_0 = {}.ElRow;
/** @type {[typeof __VLS_components.ElRow, typeof __VLS_components.elRow, typeof __VLS_components.ElRow, typeof __VLS_components.elRow, ]} */ ;
// @ts-ignore
const __VLS_1 = __VLS_asFunctionalComponent(__VLS_0, new __VLS_0({
    gutter: (12),
}));
const __VLS_2 = __VLS_1({
    gutter: (12),
}, ...__VLS_functionalComponentArgsRest(__VLS_1));
var __VLS_4 = {};
__VLS_3.slots.default;
const __VLS_5 = {}.ElCol;
/** @type {[typeof __VLS_components.ElCol, typeof __VLS_components.elCol, typeof __VLS_components.ElCol, typeof __VLS_components.elCol, ]} */ ;
// @ts-ignore
const __VLS_6 = __VLS_asFunctionalComponent(__VLS_5, new __VLS_5({
    span: (12),
}));
const __VLS_7 = __VLS_6({
    span: (12),
}, ...__VLS_functionalComponentArgsRest(__VLS_6));
__VLS_8.slots.default;
const __VLS_9 = {}.ElCard;
/** @type {[typeof __VLS_components.ElCard, typeof __VLS_components.elCard, typeof __VLS_components.ElCard, typeof __VLS_components.elCard, ]} */ ;
// @ts-ignore
const __VLS_10 = __VLS_asFunctionalComponent(__VLS_9, new __VLS_9({}));
const __VLS_11 = __VLS_10({}, ...__VLS_functionalComponentArgsRest(__VLS_10));
__VLS_12.slots.default;
{
    const { header: __VLS_thisSlot } = __VLS_12.slots;
}
const __VLS_13 = {}.ElRow;
/** @type {[typeof __VLS_components.ElRow, typeof __VLS_components.elRow, typeof __VLS_components.ElRow, typeof __VLS_components.elRow, ]} */ ;
// @ts-ignore
const __VLS_14 = __VLS_asFunctionalComponent(__VLS_13, new __VLS_13({
    gutter: (10),
    ...{ style: {} },
}));
const __VLS_15 = __VLS_14({
    gutter: (10),
    ...{ style: {} },
}, ...__VLS_functionalComponentArgsRest(__VLS_14));
__VLS_16.slots.default;
const __VLS_17 = {}.ElCol;
/** @type {[typeof __VLS_components.ElCol, typeof __VLS_components.elCol, typeof __VLS_components.ElCol, typeof __VLS_components.elCol, ]} */ ;
// @ts-ignore
const __VLS_18 = __VLS_asFunctionalComponent(__VLS_17, new __VLS_17({
    span: (8),
}));
const __VLS_19 = __VLS_18({
    span: (8),
}, ...__VLS_functionalComponentArgsRest(__VLS_18));
__VLS_20.slots.default;
const __VLS_21 = {}.ElStatistic;
/** @type {[typeof __VLS_components.ElStatistic, typeof __VLS_components.elStatistic, ]} */ ;
// @ts-ignore
const __VLS_22 = __VLS_asFunctionalComponent(__VLS_21, new __VLS_21({
    title: "总提交",
    value: (__VLS_ctx.stats.totalSubmissions || 0),
}));
const __VLS_23 = __VLS_22({
    title: "总提交",
    value: (__VLS_ctx.stats.totalSubmissions || 0),
}, ...__VLS_functionalComponentArgsRest(__VLS_22));
var __VLS_20;
const __VLS_25 = {}.ElCol;
/** @type {[typeof __VLS_components.ElCol, typeof __VLS_components.elCol, typeof __VLS_components.ElCol, typeof __VLS_components.elCol, ]} */ ;
// @ts-ignore
const __VLS_26 = __VLS_asFunctionalComponent(__VLS_25, new __VLS_25({
    span: (8),
}));
const __VLS_27 = __VLS_26({
    span: (8),
}, ...__VLS_functionalComponentArgsRest(__VLS_26));
__VLS_28.slots.default;
const __VLS_29 = {}.ElStatistic;
/** @type {[typeof __VLS_components.ElStatistic, typeof __VLS_components.elStatistic, ]} */ ;
// @ts-ignore
const __VLS_30 = __VLS_asFunctionalComponent(__VLS_29, new __VLS_29({
    title: "已通过",
    value: (__VLS_ctx.stats.acceptedSubmissions || 0),
}));
const __VLS_31 = __VLS_30({
    title: "已通过",
    value: (__VLS_ctx.stats.acceptedSubmissions || 0),
}, ...__VLS_functionalComponentArgsRest(__VLS_30));
var __VLS_28;
const __VLS_33 = {}.ElCol;
/** @type {[typeof __VLS_components.ElCol, typeof __VLS_components.elCol, typeof __VLS_components.ElCol, typeof __VLS_components.elCol, ]} */ ;
// @ts-ignore
const __VLS_34 = __VLS_asFunctionalComponent(__VLS_33, new __VLS_33({
    span: (8),
}));
const __VLS_35 = __VLS_34({
    span: (8),
}, ...__VLS_functionalComponentArgsRest(__VLS_34));
__VLS_36.slots.default;
const __VLS_37 = {}.ElStatistic;
/** @type {[typeof __VLS_components.ElStatistic, typeof __VLS_components.elStatistic, ]} */ ;
// @ts-ignore
const __VLS_38 = __VLS_asFunctionalComponent(__VLS_37, new __VLS_37({
    title: "通过率",
    value: (__VLS_ctx.stats.acceptanceRate || 0),
    suffix: "%",
}));
const __VLS_39 = __VLS_38({
    title: "通过率",
    value: (__VLS_ctx.stats.acceptanceRate || 0),
    suffix: "%",
}, ...__VLS_functionalComponentArgsRest(__VLS_38));
var __VLS_36;
var __VLS_16;
const __VLS_41 = {}.ElDivider;
/** @type {[typeof __VLS_components.ElDivider, typeof __VLS_components.elDivider, typeof __VLS_components.ElDivider, typeof __VLS_components.elDivider, ]} */ ;
// @ts-ignore
const __VLS_42 = __VLS_asFunctionalComponent(__VLS_41, new __VLS_41({
    contentPosition: "left",
}));
const __VLS_43 = __VLS_42({
    contentPosition: "left",
}, ...__VLS_functionalComponentArgsRest(__VLS_42));
__VLS_44.slots.default;
var __VLS_44;
const __VLS_45 = {}.ElTable;
/** @type {[typeof __VLS_components.ElTable, typeof __VLS_components.elTable, typeof __VLS_components.ElTable, typeof __VLS_components.elTable, ]} */ ;
// @ts-ignore
const __VLS_46 = __VLS_asFunctionalComponent(__VLS_45, new __VLS_45({
    data: (__VLS_ctx.stats.recent7Days || []),
    size: "small",
    border: true,
}));
const __VLS_47 = __VLS_46({
    data: (__VLS_ctx.stats.recent7Days || []),
    size: "small",
    border: true,
}, ...__VLS_functionalComponentArgsRest(__VLS_46));
__VLS_48.slots.default;
const __VLS_49 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_50 = __VLS_asFunctionalComponent(__VLS_49, new __VLS_49({
    prop: "date",
    label: "日期",
    width: "140",
}));
const __VLS_51 = __VLS_50({
    prop: "date",
    label: "日期",
    width: "140",
}, ...__VLS_functionalComponentArgsRest(__VLS_50));
const __VLS_53 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_54 = __VLS_asFunctionalComponent(__VLS_53, new __VLS_53({
    prop: "submissions",
    label: "提交数",
}));
const __VLS_55 = __VLS_54({
    prop: "submissions",
    label: "提交数",
}, ...__VLS_functionalComponentArgsRest(__VLS_54));
var __VLS_48;
const __VLS_57 = {}.ElDivider;
/** @type {[typeof __VLS_components.ElDivider, typeof __VLS_components.elDivider, typeof __VLS_components.ElDivider, typeof __VLS_components.elDivider, ]} */ ;
// @ts-ignore
const __VLS_58 = __VLS_asFunctionalComponent(__VLS_57, new __VLS_57({
    contentPosition: "left",
}));
const __VLS_59 = __VLS_58({
    contentPosition: "left",
}, ...__VLS_functionalComponentArgsRest(__VLS_58));
__VLS_60.slots.default;
var __VLS_60;
for (const [w, idx] of __VLS_getVForSourceType((__VLS_ctx.weakPoints))) {
    const __VLS_61 = {}.ElTag;
    /** @type {[typeof __VLS_components.ElTag, typeof __VLS_components.elTag, typeof __VLS_components.ElTag, typeof __VLS_components.elTag, ]} */ ;
    // @ts-ignore
    const __VLS_62 = __VLS_asFunctionalComponent(__VLS_61, new __VLS_61({
        key: (idx),
        type: "warning",
        ...{ style: {} },
    }));
    const __VLS_63 = __VLS_62({
        key: (idx),
        type: "warning",
        ...{ style: {} },
    }, ...__VLS_functionalComponentArgsRest(__VLS_62));
    __VLS_64.slots.default;
    (w.tagName);
    (w.wrongCount);
    var __VLS_64;
}
var __VLS_12;
var __VLS_8;
const __VLS_65 = {}.ElCol;
/** @type {[typeof __VLS_components.ElCol, typeof __VLS_components.elCol, typeof __VLS_components.ElCol, typeof __VLS_components.elCol, ]} */ ;
// @ts-ignore
const __VLS_66 = __VLS_asFunctionalComponent(__VLS_65, new __VLS_65({
    span: (12),
}));
const __VLS_67 = __VLS_66({
    span: (12),
}, ...__VLS_functionalComponentArgsRest(__VLS_66));
__VLS_68.slots.default;
const __VLS_69 = {}.ElCard;
/** @type {[typeof __VLS_components.ElCard, typeof __VLS_components.elCard, typeof __VLS_components.ElCard, typeof __VLS_components.elCard, ]} */ ;
// @ts-ignore
const __VLS_70 = __VLS_asFunctionalComponent(__VLS_69, new __VLS_69({}));
const __VLS_71 = __VLS_70({}, ...__VLS_functionalComponentArgsRest(__VLS_70));
__VLS_72.slots.default;
{
    const { header: __VLS_thisSlot } = __VLS_72.slots;
    __VLS_asFunctionalElement(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({
        ...{ style: {} },
    });
    __VLS_asFunctionalElement(__VLS_intrinsicElements.span, __VLS_intrinsicElements.span)({});
    const __VLS_73 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_74 = __VLS_asFunctionalComponent(__VLS_73, new __VLS_73({
        ...{ 'onClick': {} },
        type: "primary",
        loading: (__VLS_ctx.adviceLoading),
    }));
    const __VLS_75 = __VLS_74({
        ...{ 'onClick': {} },
        type: "primary",
        loading: (__VLS_ctx.adviceLoading),
    }, ...__VLS_functionalComponentArgsRest(__VLS_74));
    let __VLS_77;
    let __VLS_78;
    let __VLS_79;
    const __VLS_80 = {
        onClick: (__VLS_ctx.loadAdvice)
    };
    __VLS_76.slots.default;
    (__VLS_ctx.adviceLoading ? 'AI智能分析中...' : '刷新建议');
    var __VLS_76;
}
const __VLS_81 = {}.ElSkeleton;
/** @type {[typeof __VLS_components.ElSkeleton, typeof __VLS_components.elSkeleton, typeof __VLS_components.ElSkeleton, typeof __VLS_components.elSkeleton, ]} */ ;
// @ts-ignore
const __VLS_82 = __VLS_asFunctionalComponent(__VLS_81, new __VLS_81({
    loading: (__VLS_ctx.adviceLoading),
    animated: true,
    rows: (6),
}));
const __VLS_83 = __VLS_82({
    loading: (__VLS_ctx.adviceLoading),
    animated: true,
    rows: (6),
}, ...__VLS_functionalComponentArgsRest(__VLS_82));
__VLS_84.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_84.slots;
    const __VLS_85 = {}.ElAlert;
    /** @type {[typeof __VLS_components.ElAlert, typeof __VLS_components.elAlert, ]} */ ;
    // @ts-ignore
    const __VLS_86 = __VLS_asFunctionalComponent(__VLS_85, new __VLS_85({
        title: (`来源：${__VLS_ctx.adviceSource}`),
        type: "info",
        showIcon: true,
        closable: (false),
        ...{ style: {} },
    }));
    const __VLS_87 = __VLS_86({
        title: (`来源：${__VLS_ctx.adviceSource}`),
        type: "info",
        showIcon: true,
        closable: (false),
        ...{ style: {} },
    }, ...__VLS_functionalComponentArgsRest(__VLS_86));
    __VLS_asFunctionalElement(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({
        ...{ style: {} },
    });
    (__VLS_ctx.adviceText || '暂无建议');
}
var __VLS_84;
var __VLS_72;
var __VLS_68;
var __VLS_3;
var __VLS_dollars;
const __VLS_self = (await import('vue')).defineComponent({
    setup() {
        return {
            stats: stats,
            weakPoints: weakPoints,
            adviceLoading: adviceLoading,
            adviceText: adviceText,
            adviceSource: adviceSource,
            loadAdvice: loadAdvice,
        };
    },
});
export default (await import('vue')).defineComponent({
    setup() {
        return {};
    },
});
; /* PartiallyEnd: #4569/main.vue */
