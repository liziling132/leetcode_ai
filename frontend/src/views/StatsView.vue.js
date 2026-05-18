import { computed, onMounted, reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { api } from '@/api';
const stats = reactive({ totalSubmissions: 0, acceptedSubmissions: 0, acceptanceRate: 0, recent7Days: [] });
const weakPoints = ref([]);
const adviceLoading = ref(false);
const adviceText = ref('');
const adviceSource = ref('');
const adviceTouched = ref(false);
const safeRate = computed(() => {
    const v = Number(stats.acceptanceRate || 0);
    if (!Number.isFinite(v))
        return 0;
    return Math.max(0, Math.min(100, Number(v.toFixed(2))));
});
const recentTotal = computed(() => (stats.recent7Days || []).reduce((sum, d) => sum + Number(d.submissions || 0), 0));
const recentAvg = computed(() => {
    const days = (stats.recent7Days || []).length;
    if (!days)
        return 0;
    return Number((recentTotal.value / days).toFixed(2));
});
const recentMax = computed(() => Math.max(1, ...(stats.recent7Days || []).map((d) => Number(d.submissions || 0))));
const trendWidth = (v) => Math.max(4, Math.round((Number(v || 0) / recentMax.value) * 100));
const weakTotal = computed(() => weakPoints.value.reduce((sum, x) => sum + Number(x.wrongCount || 0), 0));
const weakRate = (count) => {
    if (!weakTotal.value)
        return 0;
    return Math.min(100, Math.round((Number(count || 0) / weakTotal.value) * 100));
};
const loadStats = async () => {
    const s = await api.stats();
    const w = await api.weakPoints({ size: 8 });
    Object.assign(stats, s || {});
    weakPoints.value = w?.list || [];
};
const loadAdvice = async () => {
    adviceTouched.value = true;
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
});
debugger; /* PartiallyEnd: #3632/scriptSetup.vue */
const __VLS_ctx = {};
let __VLS_components;
let __VLS_directives;
// CSS variable injection 
// CSS variable injection end 
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
const __VLS_41 = {}.ElProgress;
/** @type {[typeof __VLS_components.ElProgress, typeof __VLS_components.elProgress, ]} */ ;
// @ts-ignore
const __VLS_42 = __VLS_asFunctionalComponent(__VLS_41, new __VLS_41({
    percentage: (__VLS_ctx.safeRate),
    strokeWidth: (16),
    status: "success",
    ...{ style: {} },
}));
const __VLS_43 = __VLS_42({
    percentage: (__VLS_ctx.safeRate),
    strokeWidth: (16),
    status: "success",
    ...{ style: {} },
}, ...__VLS_functionalComponentArgsRest(__VLS_42));
const __VLS_45 = {}.ElRow;
/** @type {[typeof __VLS_components.ElRow, typeof __VLS_components.elRow, typeof __VLS_components.ElRow, typeof __VLS_components.elRow, ]} */ ;
// @ts-ignore
const __VLS_46 = __VLS_asFunctionalComponent(__VLS_45, new __VLS_45({
    gutter: (10),
    ...{ style: {} },
}));
const __VLS_47 = __VLS_46({
    gutter: (10),
    ...{ style: {} },
}, ...__VLS_functionalComponentArgsRest(__VLS_46));
__VLS_48.slots.default;
const __VLS_49 = {}.ElCol;
/** @type {[typeof __VLS_components.ElCol, typeof __VLS_components.elCol, typeof __VLS_components.ElCol, typeof __VLS_components.elCol, ]} */ ;
// @ts-ignore
const __VLS_50 = __VLS_asFunctionalComponent(__VLS_49, new __VLS_49({
    span: (12),
}));
const __VLS_51 = __VLS_50({
    span: (12),
}, ...__VLS_functionalComponentArgsRest(__VLS_50));
__VLS_52.slots.default;
const __VLS_53 = {}.ElStatistic;
/** @type {[typeof __VLS_components.ElStatistic, typeof __VLS_components.elStatistic, ]} */ ;
// @ts-ignore
const __VLS_54 = __VLS_asFunctionalComponent(__VLS_53, new __VLS_53({
    title: "近7天总提交",
    value: (__VLS_ctx.recentTotal),
}));
const __VLS_55 = __VLS_54({
    title: "近7天总提交",
    value: (__VLS_ctx.recentTotal),
}, ...__VLS_functionalComponentArgsRest(__VLS_54));
var __VLS_52;
const __VLS_57 = {}.ElCol;
/** @type {[typeof __VLS_components.ElCol, typeof __VLS_components.elCol, typeof __VLS_components.ElCol, typeof __VLS_components.elCol, ]} */ ;
// @ts-ignore
const __VLS_58 = __VLS_asFunctionalComponent(__VLS_57, new __VLS_57({
    span: (12),
}));
const __VLS_59 = __VLS_58({
    span: (12),
}, ...__VLS_functionalComponentArgsRest(__VLS_58));
__VLS_60.slots.default;
const __VLS_61 = {}.ElStatistic;
/** @type {[typeof __VLS_components.ElStatistic, typeof __VLS_components.elStatistic, ]} */ ;
// @ts-ignore
const __VLS_62 = __VLS_asFunctionalComponent(__VLS_61, new __VLS_61({
    title: "近7天日均提交",
    value: (__VLS_ctx.recentAvg),
}));
const __VLS_63 = __VLS_62({
    title: "近7天日均提交",
    value: (__VLS_ctx.recentAvg),
}, ...__VLS_functionalComponentArgsRest(__VLS_62));
var __VLS_60;
var __VLS_48;
const __VLS_65 = {}.ElDivider;
/** @type {[typeof __VLS_components.ElDivider, typeof __VLS_components.elDivider, typeof __VLS_components.ElDivider, typeof __VLS_components.elDivider, ]} */ ;
// @ts-ignore
const __VLS_66 = __VLS_asFunctionalComponent(__VLS_65, new __VLS_65({
    contentPosition: "left",
}));
const __VLS_67 = __VLS_66({
    contentPosition: "left",
}, ...__VLS_functionalComponentArgsRest(__VLS_66));
__VLS_68.slots.default;
var __VLS_68;
const __VLS_69 = {}.ElTable;
/** @type {[typeof __VLS_components.ElTable, typeof __VLS_components.elTable, typeof __VLS_components.ElTable, typeof __VLS_components.elTable, ]} */ ;
// @ts-ignore
const __VLS_70 = __VLS_asFunctionalComponent(__VLS_69, new __VLS_69({
    data: (__VLS_ctx.stats.recent7Days || []),
    size: "small",
    border: true,
}));
const __VLS_71 = __VLS_70({
    data: (__VLS_ctx.stats.recent7Days || []),
    size: "small",
    border: true,
}, ...__VLS_functionalComponentArgsRest(__VLS_70));
__VLS_72.slots.default;
const __VLS_73 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_74 = __VLS_asFunctionalComponent(__VLS_73, new __VLS_73({
    prop: "date",
    label: "日期",
    width: "140",
}));
const __VLS_75 = __VLS_74({
    prop: "date",
    label: "日期",
    width: "140",
}, ...__VLS_functionalComponentArgsRest(__VLS_74));
const __VLS_77 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_78 = __VLS_asFunctionalComponent(__VLS_77, new __VLS_77({
    prop: "submissions",
    label: "提交数",
    width: "90",
}));
const __VLS_79 = __VLS_78({
    prop: "submissions",
    label: "提交数",
    width: "90",
}, ...__VLS_functionalComponentArgsRest(__VLS_78));
const __VLS_81 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_82 = __VLS_asFunctionalComponent(__VLS_81, new __VLS_81({
    label: "趋势",
    minWidth: "180",
}));
const __VLS_83 = __VLS_82({
    label: "趋势",
    minWidth: "180",
}, ...__VLS_functionalComponentArgsRest(__VLS_82));
__VLS_84.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_84.slots;
    const [scope] = __VLS_getSlotParams(__VLS_thisSlot);
    __VLS_asFunctionalElement(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({
        ...{ class: "trend-bar-wrap" },
    });
    __VLS_asFunctionalElement(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({
        ...{ class: "trend-bar" },
        ...{ style: ({ width: __VLS_ctx.trendWidth(scope.row.submissions) + '%' }) },
    });
}
var __VLS_84;
var __VLS_72;
const __VLS_85 = {}.ElDivider;
/** @type {[typeof __VLS_components.ElDivider, typeof __VLS_components.elDivider, typeof __VLS_components.ElDivider, typeof __VLS_components.elDivider, ]} */ ;
// @ts-ignore
const __VLS_86 = __VLS_asFunctionalComponent(__VLS_85, new __VLS_85({
    contentPosition: "left",
}));
const __VLS_87 = __VLS_86({
    contentPosition: "left",
}, ...__VLS_functionalComponentArgsRest(__VLS_86));
__VLS_88.slots.default;
var __VLS_88;
const __VLS_89 = {}.ElTable;
/** @type {[typeof __VLS_components.ElTable, typeof __VLS_components.elTable, typeof __VLS_components.ElTable, typeof __VLS_components.elTable, ]} */ ;
// @ts-ignore
const __VLS_90 = __VLS_asFunctionalComponent(__VLS_89, new __VLS_89({
    data: (__VLS_ctx.weakPoints),
    size: "small",
    border: true,
}));
const __VLS_91 = __VLS_90({
    data: (__VLS_ctx.weakPoints),
    size: "small",
    border: true,
}, ...__VLS_functionalComponentArgsRest(__VLS_90));
__VLS_92.slots.default;
const __VLS_93 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_94 = __VLS_asFunctionalComponent(__VLS_93, new __VLS_93({
    type: "index",
    label: "#",
    width: "50",
}));
const __VLS_95 = __VLS_94({
    type: "index",
    label: "#",
    width: "50",
}, ...__VLS_functionalComponentArgsRest(__VLS_94));
const __VLS_97 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_98 = __VLS_asFunctionalComponent(__VLS_97, new __VLS_97({
    prop: "tagName",
    label: "知识点",
}));
const __VLS_99 = __VLS_98({
    prop: "tagName",
    label: "知识点",
}, ...__VLS_functionalComponentArgsRest(__VLS_98));
const __VLS_101 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_102 = __VLS_asFunctionalComponent(__VLS_101, new __VLS_101({
    prop: "wrongCount",
    label: "错误次数",
    width: "100",
}));
const __VLS_103 = __VLS_102({
    prop: "wrongCount",
    label: "错误次数",
    width: "100",
}, ...__VLS_functionalComponentArgsRest(__VLS_102));
const __VLS_105 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_106 = __VLS_asFunctionalComponent(__VLS_105, new __VLS_105({
    label: "占比",
    minWidth: "180",
}));
const __VLS_107 = __VLS_106({
    label: "占比",
    minWidth: "180",
}, ...__VLS_functionalComponentArgsRest(__VLS_106));
__VLS_108.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_108.slots;
    const [scope] = __VLS_getSlotParams(__VLS_thisSlot);
    const __VLS_109 = {}.ElProgress;
    /** @type {[typeof __VLS_components.ElProgress, typeof __VLS_components.elProgress, ]} */ ;
    // @ts-ignore
    const __VLS_110 = __VLS_asFunctionalComponent(__VLS_109, new __VLS_109({
        percentage: (__VLS_ctx.weakRate(scope.row.wrongCount)),
        strokeWidth: (12),
        color: "#e6a23c",
    }));
    const __VLS_111 = __VLS_110({
        percentage: (__VLS_ctx.weakRate(scope.row.wrongCount)),
        strokeWidth: (12),
        color: "#e6a23c",
    }, ...__VLS_functionalComponentArgsRest(__VLS_110));
}
var __VLS_108;
var __VLS_92;
var __VLS_12;
var __VLS_8;
const __VLS_113 = {}.ElCol;
/** @type {[typeof __VLS_components.ElCol, typeof __VLS_components.elCol, typeof __VLS_components.ElCol, typeof __VLS_components.elCol, ]} */ ;
// @ts-ignore
const __VLS_114 = __VLS_asFunctionalComponent(__VLS_113, new __VLS_113({
    span: (12),
}));
const __VLS_115 = __VLS_114({
    span: (12),
}, ...__VLS_functionalComponentArgsRest(__VLS_114));
__VLS_116.slots.default;
const __VLS_117 = {}.ElCard;
/** @type {[typeof __VLS_components.ElCard, typeof __VLS_components.elCard, typeof __VLS_components.ElCard, typeof __VLS_components.elCard, ]} */ ;
// @ts-ignore
const __VLS_118 = __VLS_asFunctionalComponent(__VLS_117, new __VLS_117({}));
const __VLS_119 = __VLS_118({}, ...__VLS_functionalComponentArgsRest(__VLS_118));
__VLS_120.slots.default;
{
    const { header: __VLS_thisSlot } = __VLS_120.slots;
    __VLS_asFunctionalElement(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({
        ...{ style: {} },
    });
    __VLS_asFunctionalElement(__VLS_intrinsicElements.span, __VLS_intrinsicElements.span)({});
    const __VLS_121 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_122 = __VLS_asFunctionalComponent(__VLS_121, new __VLS_121({
        ...{ 'onClick': {} },
        type: "primary",
        loading: (__VLS_ctx.adviceLoading),
    }));
    const __VLS_123 = __VLS_122({
        ...{ 'onClick': {} },
        type: "primary",
        loading: (__VLS_ctx.adviceLoading),
    }, ...__VLS_functionalComponentArgsRest(__VLS_122));
    let __VLS_125;
    let __VLS_126;
    let __VLS_127;
    const __VLS_128 = {
        onClick: (__VLS_ctx.loadAdvice)
    };
    __VLS_124.slots.default;
    (__VLS_ctx.adviceLoading ? 'AI智能分析中...' : '刷新建议');
    var __VLS_124;
}
const __VLS_129 = {}.ElSkeleton;
/** @type {[typeof __VLS_components.ElSkeleton, typeof __VLS_components.elSkeleton, typeof __VLS_components.ElSkeleton, typeof __VLS_components.elSkeleton, ]} */ ;
// @ts-ignore
const __VLS_130 = __VLS_asFunctionalComponent(__VLS_129, new __VLS_129({
    loading: (__VLS_ctx.adviceLoading),
    animated: true,
    rows: (6),
}));
const __VLS_131 = __VLS_130({
    loading: (__VLS_ctx.adviceLoading),
    animated: true,
    rows: (6),
}, ...__VLS_functionalComponentArgsRest(__VLS_130));
__VLS_132.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_132.slots;
    if (!__VLS_ctx.adviceTouched) {
        const __VLS_133 = {}.ElAlert;
        /** @type {[typeof __VLS_components.ElAlert, typeof __VLS_components.elAlert, ]} */ ;
        // @ts-ignore
        const __VLS_134 = __VLS_asFunctionalComponent(__VLS_133, new __VLS_133({
            title: "点击“刷新建议”后再生成学习建议。",
            type: "info",
            showIcon: true,
            closable: (false),
            ...{ style: {} },
        }));
        const __VLS_135 = __VLS_134({
            title: "点击“刷新建议”后再生成学习建议。",
            type: "info",
            showIcon: true,
            closable: (false),
            ...{ style: {} },
        }, ...__VLS_functionalComponentArgsRest(__VLS_134));
    }
    else {
        const __VLS_137 = {}.ElAlert;
        /** @type {[typeof __VLS_components.ElAlert, typeof __VLS_components.elAlert, ]} */ ;
        // @ts-ignore
        const __VLS_138 = __VLS_asFunctionalComponent(__VLS_137, new __VLS_137({
            title: (`来源：${__VLS_ctx.adviceSource}`),
            type: "info",
            showIcon: true,
            closable: (false),
            ...{ style: {} },
        }));
        const __VLS_139 = __VLS_138({
            title: (`来源：${__VLS_ctx.adviceSource}`),
            type: "info",
            showIcon: true,
            closable: (false),
            ...{ style: {} },
        }, ...__VLS_functionalComponentArgsRest(__VLS_138));
        __VLS_asFunctionalElement(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({
            ...{ style: {} },
        });
        (__VLS_ctx.adviceText || '暂无建议');
    }
}
var __VLS_132;
var __VLS_120;
var __VLS_116;
var __VLS_3;
/** @type {__VLS_StyleScopedClasses['trend-bar-wrap']} */ ;
/** @type {__VLS_StyleScopedClasses['trend-bar']} */ ;
var __VLS_dollars;
const __VLS_self = (await import('vue')).defineComponent({
    setup() {
        return {
            stats: stats,
            weakPoints: weakPoints,
            adviceLoading: adviceLoading,
            adviceText: adviceText,
            adviceSource: adviceSource,
            adviceTouched: adviceTouched,
            safeRate: safeRate,
            recentTotal: recentTotal,
            recentAvg: recentAvg,
            trendWidth: trendWidth,
            weakRate: weakRate,
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
