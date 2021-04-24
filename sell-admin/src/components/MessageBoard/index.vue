<template>
  <el-popover
    placement="bottom"
    popper-class="message-board"
    width="330"
    trigger="click"
    @show="handleShowPopover"
  >
    <div slot="reference" class="message-btn">
      <el-badge :value="messageCount">
        <svg-icon class="message-icon" icon-class="xiaoxi" />
      </el-badge>
    </div>
    <div class="message-container">
      <div class="message-title">通 知</div>
      <div class="message-list">
        <el-scrollbar v-show="list.length" ref="scrollBar" style="height: 100%;">
          <transition-group name="list-transform" tag="div">
            <div
              v-for="(item, index) in list"
              :key="item.id"
              class="message-item"
              :class="{
                'is-read': item.status === '2',
                'hide-border': index === list.length - 1 && !showLastBorder
              }"
              @click="handleRead(item)"
            >
              <div class="message-content">
                <div class="desc">{{ item.content }}</div>
                <div class="time">
                  <div class="content">{{ item.time }}</div>
                  <div class="link" @click="handleJump(item)">查看</div>
                </div>
              </div>
              <div class="message-delete">
                <i class="el-icon-close" @click="handleDeleteMsg(item)" />
              </div>
            </div>
          </transition-group>
          <div v-if="hasMore" class="loading">加载中...</div>
        </el-scrollbar>
        <div v-show="!list.length" class="no-data">
          <img src="~@/assets/svg/trumpet.svg">
          <div>你已查看所有通知</div>
        </div>
      </div>
      <div class="message-control" @click="handleReadAll">
        一键已读
      </div>
    </div>
  </el-popover>
</template>

<script>
// import { getOrderMessageList, readMessage, readAllMessage, getMessageCount, deleteMessage } from '@/api/order-message-reminder';
import { timeSince } from '@/utils/date';

export default {
  name: 'MessageBoard',
  data() {
    return {
      messageCount: 0,
      pagination: {
        pageNum: 1,
        pageSize: 10,
      },
      hasMore: true,
      loading: false,
      showLastBorder: true,
      list: [
        {
          'content': '您有新的订单【WN20080316013667】待审核！',
          'createTime': 1596441679000,
          'id': 155,
          'status': '1',
        },
        {
          'content': '您有新的订单【WN20080316002641】待审核！',
          'createTime': 1596441644000,
          'id': 154,
          'status': '1',
        },
        {
          'content': '您有新的订单【WN20080316008468】待审核！',
          'createTime': 1596441627000,
          'id': 153,
          'status': '1',
        },
        {
          'content': '您有新的订单【WN20080316002774】待审核！',
          'createTime': 1596441612000,
          'id': 152,
          'status': '2',
        },
        {
          'content': '您有新的订单【WN20080315598827】待审核！',
          'createTime': 1596441593000,
          'id': 151,
          'status': '1',
        },
      ],
      timer: null,
      timerInterval: 30000,
      flagTime: null,
    };
  },
  async mounted() {
    this.initScroll();
    this.startGetMessage();
  },
  beforeDestroy() {
    this.destroyScroll();
    this.endGetMessage();
  },
  methods: {
    handleJump() {
      console.log('go to page');
    },
    handleShowPopover() {
      this.judgeShowLastBorder();
    },
    startGetMessage() {
      this.flagTime = new Date().getTime();

      this.timer = setInterval(async () => {
        this.getMessageCount();
        await this.getMessageList();
        if (this.list.length) {
          const { createTime, content } = this.list[0];

          if (createTime > this.flagTime) {
            this.$message.success(content);
            this.flagTime = createTime;
          }
        }
      }, this.timerInterval);

      this.getMessageCount();
      this.getMessageList();
    },
    endGetMessage() {
      clearInterval(this.timer);
    },
    initScroll() {
      const scrollWrapEl = this.$refs.scrollBar.wrap;

      this._scrollHandler = async () => {
        if (this.hasMore && !this.loading) {
          if (scrollWrapEl.clientHeight + scrollWrapEl.scrollTop + 10 > scrollWrapEl.scrollHeight) {
            this.loading = true;
            this.pagination.pageSize += 10;
            await this.getMessageList();
            this.loading = false;
          }
        }
      };

      scrollWrapEl.addEventListener('scroll', this._scrollHandler);
    },
    destroyScroll() {
      const scrollWrapEl = this.$refs.scrollBar.wrap;

      scrollWrapEl.removeEventListener('scroll', this._scrollHandler);

      this._scrollHandler = null;
    },
    async getMessageList() {
      // const { list, pageNum, pageSize, hasNextPage } = await getOrderMessageList(this.pagination);
      // list.forEach(item => {
      //   item.time = timeSince(item.createTime);
      // });
      // this.list = list;
      // this.pagination = { pageNum, pageSize };
      // this.hasMore = hasNextPage;
    },
    async getMessageCount() {
      // const { data: count } = await getMessageCount();
      // this.messageCount = count;
    },
    judgeShowLastBorder() {
      // 由于使用了 transition-group 组件，vue 内部会进行过渡 dom 更新会有延迟
      setTimeout(() => {
        const scrollWrapEl = this.$refs.scrollBar.wrap;
        const scrollViewEl = scrollWrapEl.children[0];
        this.showLastBorder = scrollWrapEl.clientHeight > scrollViewEl.clientHeight;
      }, 20);
    },
    async handleRead({ id, status }) {
      // if (status === '2') return;

      // await readMessage({ id });
      // this.getMessageCount();
      // this.getMessageList();
    },
    async handleReadAll() {
      // if (!this.list.length) return;

      // await readAllMessage();
      // this.getMessageCount();
      // this.getMessageList();
    },
    async handleDeleteMsg({ id }) {
      // await deleteMessage([id]);
      // this.getMessageCount();
      // await this.getMessageList();
      // this.judgeShowLastBorder();
    },
  },
  components: {},
};
</script>

<style scoped lang="scss">
.message-btn {
  display: inline-flex;
  height: 100%;
  justify-content: center;
  align-items: center;
  line-height: normal;
  margin-right: 10px;
  cursor: pointer;
  .message-icon {
    color: rgba(0,0,0,.65);
    font-size: 20px;
  }
}
.message-container {
  .message-title {
    color: #1890ff;
    font-weight: 500;
    padding: 12px 0;
    border-bottom: 1px solid #f0f0f0;
    text-align: center;
  }
  .message-list {
    height: 300px;
    overflow-y: auto;
    .message-item {
      height: 89px;
      overflow: hidden;
      display: flex;
      cursor: pointer;
      color: rgba(0,0,0,.65);
      font-weight: 400;
      border-bottom: 1px solid #f0f0f0;
      .message-content {
        margin: 12px 0 12px 20px;
        flex: 1;
        .time {
          margin-top: 10px;
          font-size: 12px;
          display: flex;
          justify-content: space-between;
        }
      }
      .message-delete {
        flex: 0 0 30px;
        font-size: 16px;
        line-height: 64px;
        justify-content: center;
        align-items: center;
        margin: 12px 20px 12px 10px;
        i {
          cursor: pointer;
        }
      }
      &.is-read {
        color: rgba(0,0,0,.45);
        opacity: .5;
      }
      &.hide-border {
        border-bottom: 0;
      }
      &.list-transform-leave-active {
        transition: all .5s;
      }
      &.list-transform-leave-to {
        height: 0;
      }
    }
    .no-data {
      height: 100%;
      flex-direction: column;
      color: rgba(0,0,0,.45);
      display: flex;
      justify-content: center;
      align-items: center;
    }
    .loading {
      font-size: 12px;
      margin: 5px 0;
      text-align: center;
    }
  }
  .message-control {
    height: 46px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-top: 1px solid #f0f0f0;
    cursor: pointer;
  }
}
</style>
