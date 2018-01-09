# Impact of NIR on CT600 boxes

## Northern Ireland Section
### B5 NI Trading Activity

### B6 SME

### B7 NI Employer

### B8 Special Circumstances

The above boxes have been implemented and added to the HMTL and CT600 XML.

## About this return

### B125 Northern Ireland - form CT600G
CATO will not be supporting this form so this will always be false.

### B160 Trading losses brought forward set against trading profits

    B160 = CP257 = CP238 - CP283b

### B165 Net trading profits

    B165 = CP258 = CP256 - CP257

### B235 Profits before other deductions and reliefs

    Old calculation: B235 = CP265 = CP293 + CP283b (this looks wrong to me, why would profits add losses to get total profits?)
        CP293 - Total profits before other deductions and reliefs
        CP283b - Losses brought forward from on or after 01/04/2017 used against trading profit
        
    The description on the form.
        B235 = net sum of boxes 165 to 205 and 220 minus sum of boxes 225 and 230
            B170 = CP259 = CP43 (part of NTP) 
            B175 (N/A)
            B180 (N/A)
            B185 (N/A)
            B190 = CP511 = CP509 + CP510 (part of NTP) 
            B195 (N/A)
            B200 (N/A)
            B205 = CP502 (part of NTP) 
            B210 (N/A)
            B215 (N/A)
            B220 (N/A)
            B225 (N/A)
            B230 (N/A)
            
    New box B235a - Trading before other deductions and reliefs
        B235a = B165
    New box B235b - Trading before other deductions and reliefs
        B235b = sum(B170 to B230)
        
    B235 = B235a + B23b
       

### B285 Total losses carried forward and claimed against total profits
    
    Old Calculation: B285 = CP263 = CP997 + CP283b
    
    New box: B285a - Total losses carried forward and claimed against trading profit
        B285a = CP283b
    New box: B285b - Total losses carried forward and claimed against NON trading profit
        B285b = CP997

    B285 = B285a + B285b


### B295 Total of deductions and reliefs

    New box: B295a - total deductions and reliefs against trading profit
        B295a = B275 + B285a
    New box: B295b - total deductions and reliefs against NON trading profit
        B295b = sum (B240 to B265) + B285b + 290

    B295 = B295a + B295b

### B300 Profits before qualifying donations and group relief

    New box: B300a - trading profit before qualifying donations and group relief
        B300a = B235a - B295a
    New box: B300b - non trading profit before qualifying donations and group relief
        B300b = B235b - B295b

    B300 = B300a + B300b

### B315 Profits chargeable to Corporations Tax

    New box: B315a - trading profits chargeable to CT
        B315a = B300a - B305

    New box: B315b - non-trading profits chargeable to CT
        B315b = B300b

    B315 = B315a + B315b

### B325 Northern Ireland profits included

    if (qualifies and accepts NIR) B325 = B315a else = 0
