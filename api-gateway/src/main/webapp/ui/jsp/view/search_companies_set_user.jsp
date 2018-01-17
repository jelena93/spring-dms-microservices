<%@page contentType="text/html" pageEncoding="UTF-8"%>
<input class="form-control" name="company" id="company" type="text" onkeyup="search(this.value)" />
<br/>
<section class="panel">
    <table class="table" id="table-companies-set-user">
        <thead>
            <tr>
                <th><i class="icon_profile"></i> Id</th>
                <th><i class="icon_profile"></i> Name</th>
                <th><i class="icon_calendar"></i> Pib</th>
                <th><i class="icon_mail_alt"></i> Identification number</th>
                <th><i class="icon_pin_alt"></i> Headquarters</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</section>
