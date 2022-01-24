let person = {
    name:'张三',
    age:18
}

//模拟Vue2中实现响应式
//#region
let p = {}
Object.defineProperty(p,'name',{
    configurable:true,
    get(){ //有人读取name时调用
        return person.name
    },
    set(value){ //有人修改name时调用
        console.log('有人修改了name属性，我发现了，我要去更新界面！')
        person.name = value
    }
})
Object.defineProperty(p,'age',{
    get(){ //有人读取age时调用
        return person.age
    },
    set(value){ //有人修改age时调用
        console.log('有人修改了age属性，我发现了，我要去更新界面！')
        person.age = value
    }
})

p.name = "www"