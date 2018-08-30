
/**
 * Get Transaction list Service
 */
@Transaction
public Object[] getTxnVoList(){
    /* query transaction from DB */
    List<TxnRecord> txnList = getTxnList();

    /* ticketNo is key, value is ticket life circle record list*/
    Map<String, List<TxnRecord>> tktLifeCircleListMap = new HashMap<>();
    Map<String,List<TxnRecord>> grpDetailMap = new HashMap<>();

    /* 1: Process Ticket List Circle*/
    for(TxnRecord txnRecord : txnList){
        String ticketNo = txnRecord.getTicketNo();
        if(txnRecord.getTmsTktPkgCode.equal(16) && txnRecord.getTmsTktPkgCode.equal(19) && txnRecord.getTmsTktPkgCode.equal(25)){
            /* General group small ticket detail map */
            if(grpDetailMap.hasContainKey(ticketNo)){
                /*Has ticketNo append record to the grpDetailMap*/
                List<TxnRecord> txnRecordlist = grpDetailMap.get(ticketNo);
                txnRecordList.add(txnRecord);
                grpDetailMap.put(ticketNo,txnRecordList);
            }else{
                /*Not has ticketNo create new grpDetailMap*/
                List<TxnRecord> newTxnRecordlist = new ArrayList<>();
                newTxnRecordlist.add(txnRecord);
                grpDetailMap.put(ticketNo,txnRecordList);
            }
            /* Converter small group ticket to big group ticket */
            txn.setTicketNo(txn.getGrpBigTktNo);
            //TODO add parameter from group ticket need
        }
        //if has big group ticket append to the tktLifeCircleList

        if(tktLifeCircleListMap.hasContainKey(ticketNo)){
            /*Has ticketNo append record to the lifeCircleList*/
            List<TxnRecord> txnRecordlist = tktLifeCircleListMap.get(ticketNo);
            txnRecordList.add(txnRecord);
            tktLifeCircleListMap.put(ticketNo,txnRecordList);
        }else{
            /*Not has ticketNo create new lifeCircleList*/
            List<TxnRecord> newTxnRecordlist = new ArrayList<>();
            newTxnRecordlist.add(txnRecord);
            tktLifeCircleListMap.put(ticketNo,txnRecordList);
        }
    }

    /* 2: General txnVoList to view page */
    List<TxnRecord> txnVoList = new ArrayList<>();
    for (String ticketNo : map.keySet()) {
        List<TxnRecord> txnRecordList = map.get(ticketNo);
        txnRecordList.sort(Object o1, Object o2 -> {
            if(o1.getTxnDatetime().equal(o2.getTxnDatetime())){
                if(o1.getTxnDatetime() > o2.getTxnDatetime()) return 1;
                if(o1.getTxnDatetime() < o2.getTxnDatetime()) return -1;
            }else{
                //You can Compare other parameter
                return 0;
            }
        });
        /* fetch last status txn record show to the view page */
        TxnRecord txn = txnRecordList.get(0);
        txnVoList.add(txn);
    }

    //Sent result to client
    return new Object[]{txnVoList,grpDetailMap};
}




