import { onMounted, onUnmounted, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { api } from '@/api';
const list = ref([]);
const aiText = ref('');
const page = ref(1);
const size = ref(20);
const total = ref(0);
const explainLoadingId = ref(null);
const detailVisible = ref(false);
const detail = ref({});
const caseVisible = ref(false);
const caseList = ref([]);
const caseTotal = ref(0);
const casePage = ref(1);
const caseSize = ref(10);
const currentSubmissionId = ref(null);
const versionVisible = ref(false);
const versionList = ref([]);
let timer = null;
const statusText = (status) => ({
    PENDING: '待判题', JUDGING: '判题中', ACCEPTED: '通过', WRONG_ANSWER: '答案错误',
    COMPILE_ERROR: '编译错误', RUNTIME_ERROR: '运行错误', TIME_LIMIT_EXCEEDED: '超时',
    MEMORY_LIMIT_EXCEEDED: '超内存', SYSTEM_ERROR: '系统错误'
}[status] || status || '-');
const statusTagType = (status) => status === 'ACCEPTED' ? 'success' : (status === 'PENDING' || status === 'JUDGING' ? 'warning' : 'info');
const hasRunning = () => list.value.some((x) => x.judgeStatus === 'PENDING' || x.judgeStatus === 'JUDGING');
const startPollingIfNeeded = () => {
    if (!hasRunning() || timer != null)
        return;
    timer = window.setInterval(async () => {
        await load(false);
        if (!hasRunning() && timer != null) {
            window.clearInterval(timer);
            timer = null;
        }
    }, 2000);
};
const load = async (managePolling = true) => {
    const data = await api.submissions({ page: page.value, size: size.value });
    list.value = data.list || [];
    total.value = data.total || 0;
    if (managePolling)
        startPollingIfNeeded();
};
const onSizeChange = async () => { page.value = 1; await load(); };
const showDetail = async (id) => { detail.value = await api.submissionDetail(id); detailVisible.value = true; };
const loadCases = async () => {
    if (!currentSubmissionId.value)
        return;
    const data = await api.submissionCaseResults(currentSubmissionId.value, { page: casePage.value, size: caseSize.value });
    caseList.value = data.list || [];
    caseTotal.value = data.total || 0;
};
const showCases = async (id) => {
    currentSubmissionId.value = id;
    casePage.value = 1;
    await loadCases();
    caseVisible.value = true;
};
const showVersions = async (problemId) => {
    const data = await api.submissionVersions(problemId, { page: 1, size: 20 });
    versionList.value = data.list || [];
    versionVisible.value = true;
};
const explain = async (id) => {
    explainLoadingId.value = id;
    try {
        const data = await api.explain(id);
        aiText.value = data.explanation || '';
    }
    catch (e) {
        ElMessage.error(e?.message || 'AI解释失败');
    }
    finally {
        explainLoadingId.value = null;
    }
};
onMounted(load);
onUnmounted(() => { if (timer != null)
    window.clearInterval(timer); });
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
    label: "提交ID",
    width: "110",
}));
const __VLS_11 = __VLS_10({
    prop: "id",
    label: "提交ID",
    width: "110",
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
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_18 = __VLS_asFunctionalComponent(__VLS_17, new __VLS_17({
    label: "状态",
    width: "150",
}));
const __VLS_19 = __VLS_18({
    label: "状态",
    width: "150",
}, ...__VLS_functionalComponentArgsRest(__VLS_18));
__VLS_20.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_20.slots;
    const [scope] = __VLS_getSlotParams(__VLS_thisSlot);
    const __VLS_21 = {}.ElTag;
    /** @type {[typeof __VLS_components.ElTag, typeof __VLS_components.elTag, typeof __VLS_components.ElTag, typeof __VLS_components.elTag, ]} */ ;
    // @ts-ignore
    const __VLS_22 = __VLS_asFunctionalComponent(__VLS_21, new __VLS_21({
        type: (__VLS_ctx.statusTagType(scope.row.judgeStatus)),
    }));
    const __VLS_23 = __VLS_22({
        type: (__VLS_ctx.statusTagType(scope.row.judgeStatus)),
    }, ...__VLS_functionalComponentArgsRest(__VLS_22));
    __VLS_24.slots.default;
    (__VLS_ctx.statusText(scope.row.judgeStatus));
    var __VLS_24;
}
var __VLS_20;
const __VLS_25 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_26 = __VLS_asFunctionalComponent(__VLS_25, new __VLS_25({
    label: "详情",
    width: "80",
}));
const __VLS_27 = __VLS_26({
    label: "详情",
    width: "80",
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
            __VLS_ctx.showDetail(scope.row.id);
        }
    };
    __VLS_32.slots.default;
    var __VLS_32;
}
var __VLS_28;
const __VLS_37 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_38 = __VLS_asFunctionalComponent(__VLS_37, new __VLS_37({
    label: "测试点",
    width: "90",
}));
const __VLS_39 = __VLS_38({
    label: "测试点",
    width: "90",
}, ...__VLS_functionalComponentArgsRest(__VLS_38));
__VLS_40.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_40.slots;
    const [scope] = __VLS_getSlotParams(__VLS_thisSlot);
    const __VLS_41 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_42 = __VLS_asFunctionalComponent(__VLS_41, new __VLS_41({
        ...{ 'onClick': {} },
        link: true,
        type: "warning",
    }));
    const __VLS_43 = __VLS_42({
        ...{ 'onClick': {} },
        link: true,
        type: "warning",
    }, ...__VLS_functionalComponentArgsRest(__VLS_42));
    let __VLS_45;
    let __VLS_46;
    let __VLS_47;
    const __VLS_48 = {
        onClick: (...[$event]) => {
            __VLS_ctx.showCases(scope.row.id);
        }
    };
    __VLS_44.slots.default;
    var __VLS_44;
}
var __VLS_40;
const __VLS_49 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_50 = __VLS_asFunctionalComponent(__VLS_49, new __VLS_49({
    label: "版本",
    width: "90",
}));
const __VLS_51 = __VLS_50({
    label: "版本",
    width: "90",
}, ...__VLS_functionalComponentArgsRest(__VLS_50));
__VLS_52.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_52.slots;
    const [scope] = __VLS_getSlotParams(__VLS_thisSlot);
    const __VLS_53 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_54 = __VLS_asFunctionalComponent(__VLS_53, new __VLS_53({
        ...{ 'onClick': {} },
        link: true,
    }));
    const __VLS_55 = __VLS_54({
        ...{ 'onClick': {} },
        link: true,
    }, ...__VLS_functionalComponentArgsRest(__VLS_54));
    let __VLS_57;
    let __VLS_58;
    let __VLS_59;
    const __VLS_60 = {
        onClick: (...[$event]) => {
            __VLS_ctx.showVersions(scope.row.problemId);
        }
    };
    __VLS_56.slots.default;
    var __VLS_56;
}
var __VLS_52;
const __VLS_61 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_62 = __VLS_asFunctionalComponent(__VLS_61, new __VLS_61({
    label: "AI解释",
    width: "180",
}));
const __VLS_63 = __VLS_62({
    label: "AI解释",
    width: "180",
}, ...__VLS_functionalComponentArgsRest(__VLS_62));
__VLS_64.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_64.slots;
    const [scope] = __VLS_getSlotParams(__VLS_thisSlot);
    const __VLS_65 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_66 = __VLS_asFunctionalComponent(__VLS_65, new __VLS_65({
        ...{ 'onClick': {} },
        link: true,
        type: "primary",
        loading: (__VLS_ctx.explainLoadingId === scope.row.id),
    }));
    const __VLS_67 = __VLS_66({
        ...{ 'onClick': {} },
        link: true,
        type: "primary",
        loading: (__VLS_ctx.explainLoadingId === scope.row.id),
    }, ...__VLS_functionalComponentArgsRest(__VLS_66));
    let __VLS_69;
    let __VLS_70;
    let __VLS_71;
    const __VLS_72 = {
        onClick: (...[$event]) => {
            __VLS_ctx.explain(scope.row.id);
        }
    };
    __VLS_68.slots.default;
    (__VLS_ctx.explainLoadingId === scope.row.id ? 'AI智能分析中...' : '查看');
    var __VLS_68;
}
var __VLS_64;
var __VLS_8;
__VLS_asFunctionalElement(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({
    ...{ style: {} },
});
const __VLS_73 = {}.ElPagination;
/** @type {[typeof __VLS_components.ElPagination, typeof __VLS_components.elPagination, ]} */ ;
// @ts-ignore
const __VLS_74 = __VLS_asFunctionalComponent(__VLS_73, new __VLS_73({
    ...{ 'onCurrentChange': {} },
    ...{ 'onSizeChange': {} },
    currentPage: (__VLS_ctx.page),
    pageSize: (__VLS_ctx.size),
    layout: "total, sizes, prev, pager, next, jumper",
    pageSizes: ([10, 20, 50]),
    total: (__VLS_ctx.total),
}));
const __VLS_75 = __VLS_74({
    ...{ 'onCurrentChange': {} },
    ...{ 'onSizeChange': {} },
    currentPage: (__VLS_ctx.page),
    pageSize: (__VLS_ctx.size),
    layout: "total, sizes, prev, pager, next, jumper",
    pageSizes: ([10, 20, 50]),
    total: (__VLS_ctx.total),
}, ...__VLS_functionalComponentArgsRest(__VLS_74));
let __VLS_77;
let __VLS_78;
let __VLS_79;
const __VLS_80 = {
    onCurrentChange: (__VLS_ctx.load)
};
const __VLS_81 = {
    onSizeChange: (__VLS_ctx.onSizeChange)
};
var __VLS_76;
const __VLS_82 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_83 = __VLS_asFunctionalComponent(__VLS_82, new __VLS_82({
    modelValue: (__VLS_ctx.aiText),
    type: "textarea",
    rows: (8),
    ...{ style: {} },
    placeholder: "点击“查看”显示 AI 代码解释（含错误分析）",
}));
const __VLS_84 = __VLS_83({
    modelValue: (__VLS_ctx.aiText),
    type: "textarea",
    rows: (8),
    ...{ style: {} },
    placeholder: "点击“查看”显示 AI 代码解释（含错误分析）",
}, ...__VLS_functionalComponentArgsRest(__VLS_83));
const __VLS_86 = {}.ElDialog;
/** @type {[typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, ]} */ ;
// @ts-ignore
const __VLS_87 = __VLS_asFunctionalComponent(__VLS_86, new __VLS_86({
    modelValue: (__VLS_ctx.detailVisible),
    title: "提交详情",
    width: "780px",
}));
const __VLS_88 = __VLS_87({
    modelValue: (__VLS_ctx.detailVisible),
    title: "提交详情",
    width: "780px",
}, ...__VLS_functionalComponentArgsRest(__VLS_87));
__VLS_89.slots.default;
__VLS_asFunctionalElement(__VLS_intrinsicElements.pre, __VLS_intrinsicElements.pre)({});
(JSON.stringify(__VLS_ctx.detail, null, 2));
var __VLS_89;
const __VLS_90 = {}.ElDialog;
/** @type {[typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, ]} */ ;
// @ts-ignore
const __VLS_91 = __VLS_asFunctionalComponent(__VLS_90, new __VLS_90({
    modelValue: (__VLS_ctx.caseVisible),
    title: "测试点明细",
    width: "900px",
}));
const __VLS_92 = __VLS_91({
    modelValue: (__VLS_ctx.caseVisible),
    title: "测试点明细",
    width: "900px",
}, ...__VLS_functionalComponentArgsRest(__VLS_91));
__VLS_93.slots.default;
const __VLS_94 = {}.ElTable;
/** @type {[typeof __VLS_components.ElTable, typeof __VLS_components.elTable, typeof __VLS_components.ElTable, typeof __VLS_components.elTable, ]} */ ;
// @ts-ignore
const __VLS_95 = __VLS_asFunctionalComponent(__VLS_94, new __VLS_94({
    data: (__VLS_ctx.caseList),
}));
const __VLS_96 = __VLS_95({
    data: (__VLS_ctx.caseList),
}, ...__VLS_functionalComponentArgsRest(__VLS_95));
__VLS_97.slots.default;
const __VLS_98 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_99 = __VLS_asFunctionalComponent(__VLS_98, new __VLS_98({
    prop: "caseIndex",
    label: "#",
    width: "70",
}));
const __VLS_100 = __VLS_99({
    prop: "caseIndex",
    label: "#",
    width: "70",
}, ...__VLS_functionalComponentArgsRest(__VLS_99));
const __VLS_102 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_103 = __VLS_asFunctionalComponent(__VLS_102, new __VLS_102({
    prop: "judgeStatus",
    label: "状态",
    width: "140",
}));
const __VLS_104 = __VLS_103({
    prop: "judgeStatus",
    label: "状态",
    width: "140",
}, ...__VLS_functionalComponentArgsRest(__VLS_103));
const __VLS_106 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_107 = __VLS_asFunctionalComponent(__VLS_106, new __VLS_106({
    prop: "expectedOutput",
    label: "期望输出",
}));
const __VLS_108 = __VLS_107({
    prop: "expectedOutput",
    label: "期望输出",
}, ...__VLS_functionalComponentArgsRest(__VLS_107));
const __VLS_110 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_111 = __VLS_asFunctionalComponent(__VLS_110, new __VLS_110({
    prop: "actualOutput",
    label: "实际输出",
}));
const __VLS_112 = __VLS_111({
    prop: "actualOutput",
    label: "实际输出",
}, ...__VLS_functionalComponentArgsRest(__VLS_111));
var __VLS_97;
__VLS_asFunctionalElement(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({
    ...{ style: {} },
});
const __VLS_114 = {}.ElPagination;
/** @type {[typeof __VLS_components.ElPagination, typeof __VLS_components.elPagination, ]} */ ;
// @ts-ignore
const __VLS_115 = __VLS_asFunctionalComponent(__VLS_114, new __VLS_114({
    ...{ 'onCurrentChange': {} },
    currentPage: (__VLS_ctx.casePage),
    pageSize: (__VLS_ctx.caseSize),
    layout: "total, prev, pager, next",
    total: (__VLS_ctx.caseTotal),
}));
const __VLS_116 = __VLS_115({
    ...{ 'onCurrentChange': {} },
    currentPage: (__VLS_ctx.casePage),
    pageSize: (__VLS_ctx.caseSize),
    layout: "total, prev, pager, next",
    total: (__VLS_ctx.caseTotal),
}, ...__VLS_functionalComponentArgsRest(__VLS_115));
let __VLS_118;
let __VLS_119;
let __VLS_120;
const __VLS_121 = {
    onCurrentChange: (__VLS_ctx.loadCases)
};
var __VLS_117;
var __VLS_93;
const __VLS_122 = {}.ElDialog;
/** @type {[typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, ]} */ ;
// @ts-ignore
const __VLS_123 = __VLS_asFunctionalComponent(__VLS_122, new __VLS_122({
    modelValue: (__VLS_ctx.versionVisible),
    title: "历史版本",
    width: "900px",
}));
const __VLS_124 = __VLS_123({
    modelValue: (__VLS_ctx.versionVisible),
    title: "历史版本",
    width: "900px",
}, ...__VLS_functionalComponentArgsRest(__VLS_123));
__VLS_125.slots.default;
const __VLS_126 = {}.ElTable;
/** @type {[typeof __VLS_components.ElTable, typeof __VLS_components.elTable, typeof __VLS_components.ElTable, typeof __VLS_components.elTable, ]} */ ;
// @ts-ignore
const __VLS_127 = __VLS_asFunctionalComponent(__VLS_126, new __VLS_126({
    data: (__VLS_ctx.versionList),
}));
const __VLS_128 = __VLS_127({
    data: (__VLS_ctx.versionList),
}, ...__VLS_functionalComponentArgsRest(__VLS_127));
__VLS_129.slots.default;
const __VLS_130 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_131 = __VLS_asFunctionalComponent(__VLS_130, new __VLS_130({
    prop: "submissionId",
    label: "提交ID",
    width: "100",
}));
const __VLS_132 = __VLS_131({
    prop: "submissionId",
    label: "提交ID",
    width: "100",
}, ...__VLS_functionalComponentArgsRest(__VLS_131));
const __VLS_134 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_135 = __VLS_asFunctionalComponent(__VLS_134, new __VLS_134({
    prop: "judgeStatus",
    label: "状态",
    width: "140",
}));
const __VLS_136 = __VLS_135({
    prop: "judgeStatus",
    label: "状态",
    width: "140",
}, ...__VLS_functionalComponentArgsRest(__VLS_135));
const __VLS_138 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_139 = __VLS_asFunctionalComponent(__VLS_138, new __VLS_138({
    prop: "language",
    label: "语言",
    width: "90",
}));
const __VLS_140 = __VLS_139({
    prop: "language",
    label: "语言",
    width: "90",
}, ...__VLS_functionalComponentArgsRest(__VLS_139));
const __VLS_142 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_143 = __VLS_asFunctionalComponent(__VLS_142, new __VLS_142({
    prop: "submittedAt",
    label: "提交时间",
    width: "180",
}));
const __VLS_144 = __VLS_143({
    prop: "submittedAt",
    label: "提交时间",
    width: "180",
}, ...__VLS_functionalComponentArgsRest(__VLS_143));
const __VLS_146 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_147 = __VLS_asFunctionalComponent(__VLS_146, new __VLS_146({
    prop: "codeContent",
    label: "代码",
    showOverflowTooltip: true,
}));
const __VLS_148 = __VLS_147({
    prop: "codeContent",
    label: "代码",
    showOverflowTooltip: true,
}, ...__VLS_functionalComponentArgsRest(__VLS_147));
var __VLS_129;
var __VLS_125;
var __VLS_3;
var __VLS_dollars;
const __VLS_self = (await import('vue')).defineComponent({
    setup() {
        return {
            list: list,
            aiText: aiText,
            page: page,
            size: size,
            total: total,
            explainLoadingId: explainLoadingId,
            detailVisible: detailVisible,
            detail: detail,
            caseVisible: caseVisible,
            caseList: caseList,
            caseTotal: caseTotal,
            casePage: casePage,
            caseSize: caseSize,
            versionVisible: versionVisible,
            versionList: versionList,
            statusText: statusText,
            statusTagType: statusTagType,
            load: load,
            onSizeChange: onSizeChange,
            showDetail: showDetail,
            loadCases: loadCases,
            showCases: showCases,
            showVersions: showVersions,
            explain: explain,
        };
    },
});
export default (await import('vue')).defineComponent({
    setup() {
        return {};
    },
});
; /* PartiallyEnd: #4569/main.vue */
