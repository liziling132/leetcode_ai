import { onMounted, ref } from 'vue';
import { api } from '@/api';
const list = ref([]);
const aiText = ref('');
const load = async () => { const data = await api.submissions({ page: 1, size: 20 }); list.value = data.list || []; };
const explain = async (id) => { const data = await api.explain(id); aiText.value = data.explanation; };
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
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_18 = __VLS_asFunctionalComponent(__VLS_17, new __VLS_17({
    prop: "judgeStatus",
    label: "状态",
    width: "160",
}));
const __VLS_19 = __VLS_18({
    prop: "judgeStatus",
    label: "状态",
    width: "160",
}, ...__VLS_functionalComponentArgsRest(__VLS_18));
const __VLS_21 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_22 = __VLS_asFunctionalComponent(__VLS_21, new __VLS_21({
    label: "AI解释",
    width: "160",
}));
const __VLS_23 = __VLS_22({
    label: "AI解释",
    width: "160",
}, ...__VLS_functionalComponentArgsRest(__VLS_22));
__VLS_24.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_24.slots;
    const [scope] = __VLS_getSlotParams(__VLS_thisSlot);
    const __VLS_25 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_26 = __VLS_asFunctionalComponent(__VLS_25, new __VLS_25({
        ...{ 'onClick': {} },
        link: true,
        type: "primary",
    }));
    const __VLS_27 = __VLS_26({
        ...{ 'onClick': {} },
        link: true,
        type: "primary",
    }, ...__VLS_functionalComponentArgsRest(__VLS_26));
    let __VLS_29;
    let __VLS_30;
    let __VLS_31;
    const __VLS_32 = {
        onClick: (...[$event]) => {
            __VLS_ctx.explain(scope.row.id);
        }
    };
    __VLS_28.slots.default;
    var __VLS_28;
}
var __VLS_24;
var __VLS_8;
const __VLS_33 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_34 = __VLS_asFunctionalComponent(__VLS_33, new __VLS_33({
    modelValue: (__VLS_ctx.aiText),
    type: "textarea",
    rows: (8),
    ...{ style: {} },
}));
const __VLS_35 = __VLS_34({
    modelValue: (__VLS_ctx.aiText),
    type: "textarea",
    rows: (8),
    ...{ style: {} },
}, ...__VLS_functionalComponentArgsRest(__VLS_34));
var __VLS_3;
var __VLS_dollars;
const __VLS_self = (await import('vue')).defineComponent({
    setup() {
        return {
            list: list,
            aiText: aiText,
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
