package Dashboardimp;

public interface Dashboard {
    interface view{
        void enableinputs();
        void disableinputs();
        void fileeditext(String number);
        void onerror(String error);

    }
    interface presentmodel{
        void onsave();
        void     onchange();
        void  ondestroy();
    }
    interface model{
        void chargenumber();
        void setnumber(String number);

    }
    interface talklistener {
        void  onsucesssave();
        void  onsucesscharge(String number);
        void onerror(String error);

    }
}
