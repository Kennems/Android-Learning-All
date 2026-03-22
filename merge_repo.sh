#!/bin/bash

# 定义所有需要合并的子仓库 URL 列表
REPOS=(
    "https://github.com/Kennems/LearnAndroidWeb"
    "https://github.com/Kennems/LearnGlide"
    "https://github.com/Kennems/LearnARouter"
    "https://github.com/Kennems/WeCompose"
    "https://github.com/Kennems/intro-coroutines"
    "https://github.com/Kennems/JetpackComposeLayouts"
    "https://github.com/Kennems/CoroutineCompare"
    "https://github.com/Kennems/LearnJetpack"
    "https://github.com/Kennems/LearnAndroid"
    "https://github.com/Kennems/LearnHilt"
)

echo "开始批量合并子仓库到当前主仓库..."

for REPO_URL in "${REPOS[@]}"; do
    # 提取 URL 的最后一部分作为文件夹的名称 (例如 LearnGlide)
    DIR_NAME=$(basename "$REPO_URL")
    
    # 确保 URL 以 .git 结尾以符合规范
    GIT_URL="${REPO_URL}.git"

    echo "---------------------------------------------------"
    echo "▶ 正在处理: $DIR_NAME"
    echo "---------------------------------------------------"

    # 尝试使用 main 分支进行合并 (--squash 参数会将子仓库历史压缩为一次提交)
    git subtree add --prefix="$DIR_NAME" "$GIT_URL" main --squash

    # 检查上一条命令是否执行成功 (退出状态码为 0)
    if [ $? -ne 0 ]; then
        echo "⚠️ 警告: 尝试 pull 'main' 分支失败，正在尝试 'master' 分支..."
        # 降级尝试 master 分支
        git subtree add --prefix="$DIR_NAME" "$GIT_URL" master --squash
        
        if [ $? -ne 0 ]; then
            echo "❌ 错误: $DIR_NAME 合并失败，请手动检查该仓库的分支名或网络状态。"
        else
            echo "✅ 成功: $DIR_NAME (master 分支) 已合并。"
        fi
    else
        echo "✅ 成功: $DIR_NAME (main 分支) 已合并。"
    fi
done

echo "---------------------------------------------------"
echo "🎉 所有子仓库合并脚本执行完毕！"
echo "---------------------------------------------------"
